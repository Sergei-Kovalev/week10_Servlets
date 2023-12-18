package com.gmail.kovalev.service.impl;

import com.gmail.kovalev.dao.FacultyDAO;
import com.gmail.kovalev.dao.impl.FacultyDAOImpl;
import com.gmail.kovalev.dao.impl.FacultyDAOProxy;
import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.entity.Faculty;
import com.gmail.kovalev.mapper.FacultyMapper;
import com.gmail.kovalev.mapper.FacultyMapperImpl;
import com.gmail.kovalev.service.FacultyService;
import com.gmail.kovalev.util.FacultyCardGenerator;
import com.gmail.kovalev.validator.FacultyDTOValidator;
import com.gmail.kovalev.validator.FacultyInfoDTOValidator;
import com.gmail.kovalev.validator.impl.FacultyDTOValidatorImpl;
import com.gmail.kovalev.validator.impl.FacultyInfoDTOValidatorImpl;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Sergey Kovalev
 * реализация интерфейсов и его методов:
 * @see FacultyService
 */
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    /**
     * Это поле для загрузки маппеоа.
     * @see FacultyMapper
     */
    private final FacultyMapper mapper;

    /**
     * Это поле для загрузки dao, получающего данные из БД.
     * @see FacultyDAO
     */
    private final FacultyDAO facultyDAO;

    /**
     * Это поле для загрузки валидатора для {@link FacultyDTO}.
     * @see FacultyDTOValidator
     */
    private final FacultyDTOValidator facultyDTOValidator;

    /**
     * Это поле для загрузки валидатора для {@link FacultyInfoDTO}.
     * @see FacultyInfoDTOValidator
     */
    private final FacultyInfoDTOValidator facultyInfoDTOValidator;

    /**
     * Это поле для загрузки генератора PDF страницы с карточкой факультета.
     * @see FacultyCardGenerator
     */
    private final FacultyCardGenerator facultyCardPDFGenerator;

    /**
     * Конструктор класса. Загружает необходимые имплементации сервисов.
     * facultyDAO тянет прокси объект
     */
    public FacultyServiceImpl() {
        this.mapper = new FacultyMapperImpl();
        this.facultyDAO = (FacultyDAO) Proxy.newProxyInstance(
                FacultyDAO.class.getClassLoader(), new Class[] {FacultyDAO.class}, new FacultyDAOProxy(new FacultyDAOImpl()));
//        this.facultyDAO = new FacultyDAOImpl();
        this.facultyDTOValidator = new FacultyDTOValidatorImpl();
        this.facultyInfoDTOValidator = new FacultyInfoDTOValidatorImpl();
        this.facultyCardPDFGenerator = new FacultyCardGenerator();
    }

    /**
     * Метод для получения объекта {@link FacultyInfoDTO} для передачи на фронт(UI)
     * @param uuid - принимает id факультатива
     * @return объект для передачи на фронт
     */
    @Override
    public FacultyInfoDTO findFacultyById(UUID uuid) {
        Faculty faculty = facultyDAO.findFacultyById(uuid);
        FacultyInfoDTO facultyInfoDTO = mapper.fromEntityToInfoDTO(faculty);
        FacultyInfoDTO validFacultyInfoDTO = facultyInfoDTOValidator.validate(facultyInfoDTO);
        facultyCardPDFGenerator.facultyCardOutputInFile(validFacultyInfoDTO);
        return validFacultyInfoDTO;
    }

    /**
     * Метод для получения всех объектов {@link FacultyInfoDTO} для передачи на фронт(UI)
     * @return список объектов
     */
    @Override
    public List<FacultyInfoDTO> findAllFaculties() {
        return facultyDAO.findAllFaculties().stream()
                .map(mapper::fromEntityToInfoDTO)
                .peek(facultyInfoDTOValidator::validate)
                .peek(facultyCardPDFGenerator::facultyCardOutputInFile)
                .collect(Collectors.toList());
    }

    /**
     * Метод для сохранения нового объекта в БД
     * @param facultyDTO - объект для сохранения {@link FacultyDTO}
     * @return строку об успешном сохранении
     */
    @Override
    public String saveFaculty(FacultyDTO facultyDTO) {
        FacultyDTO validated = facultyDTOValidator.validate(facultyDTO);
        Faculty faculty = mapper.toFaculty(validated);
        return facultyDAO.saveFaculty(faculty);
    }

    /**
     * Метод для обновления уже существующего объекта
     * @param uuid - id объекта в БД
     * @param facultyDTO - объект с обновлёнными данными
     * @return строку об успешном обновлении
     */
    @Override
    public String updateFaculty(UUID uuid, FacultyDTO facultyDTO) {
        FacultyDTO validated = facultyDTOValidator.validate(facultyDTO);
        Faculty faculty = mapper.merge(facultyDAO.findFacultyById(uuid), validated);
        return facultyDAO.updateFaculty(faculty);
    }

    /**
     * Метод для удаления объекта из БД
     * @param uuid - id объекта в БД
     * @return - строку об успешном удалении
     */
    @Override
    public String deleteFacultyByUUID(UUID uuid) {
        return facultyDAO.deleteFacultyByUUID(uuid);
    }

    @Override
    public String rollbackDeletedFaculty() {
        return facultyDAO.rollbackDeletedFaculty();
    }
}
