/*
   Copyright (c) 2012 Emitrom LLC. All rights reserved. 
   For licensing questions, please contact us at licensing@emitrom.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.emitrom.lienzo.shared.core.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Serialization mode of a Picture.
 */
public enum SerializationMode implements EnumWithValue
{
    URL("url"), DATA_URL("data-url"), RESOURCE_ID("resource-id");

    private final String m_value;

    private SerializationMode(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }

    public static final SerializationMode lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            SerializationMode[] values = SerializationMode.values();

            for (int i = 0; i < values.length; i++)
            {
                SerializationMode value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return URL;
    }

    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        SerializationMode[] values = SerializationMode.values();

        for (int i = 0; i < values.length; i++)
        {
            keys.add(values[i].getValue());
        }
        return keys;
    }

    public static final List<SerializationMode> getValues()
    {
        return Arrays.asList(SerializationMode.values());
    }
}
