package com.gmail.kovalev.saver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component("storage")
public class Storage {
    Save save;
}
