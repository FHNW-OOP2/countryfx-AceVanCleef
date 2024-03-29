/*
 * Copyright 2016 Andreas Fester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.fhnw.cuie.cloud_save_control.libs.svgloader.FranzXaver.src.main.java.afester.javafx.components;

import ch.fhnw.cuie.cloud_save_control.libs.svgloader.FranzXaver.src.main.java.afester.javafx.components.*;
import javafx.event.Event;
import javafx.event.EventType;

public class DigitChangeEvent extends Event {

    private static final long serialVersionUID = -8487702664839335663L;
    
    private static final EventType<DigitChangeEvent> EVENT_TYPE = 
                                            new EventType<>("DIGIT_CHANGE_EVENT");

    public DigitChangeEvent(ch.fhnw.cuie.cloud_save_control.libs.svgloader.FranzXaver.src.main.java.afester.javafx.components.MultiSegment digit) {
        super(digit, null, EVENT_TYPE);
    }
}
