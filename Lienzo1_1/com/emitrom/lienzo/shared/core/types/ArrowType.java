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
 * ArrowType defines the style of the arrow heads for an 
 * {@link com.emitrom.lienzo.client.core.shape.Arrow Arrow}.
 * See the {@link com.emitrom.lienzo.client.core.shape.Arrow Arrow} 
 * class for a detailed description.
 * 
 * @see {@link com.emitrom.lienzo.client.core.shape.Arrow Arrow}
 */
public enum ArrowType implements EnumWithValue
{
    AT_END("at-end"), AT_START("at-start"), AT_BOTH_ENDS("at-both-ends"), AT_END_TAPERED("at-end-tapered"), AT_START_TAPERED("at-start-tapered");

    private final String m_value;

    private ArrowType(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }

    public static final ArrowType lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            ArrowType[] values = ArrowType.values();

            for (int i = 0; i < values.length; i++)
            {
                ArrowType value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return AT_END;
    }

    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        ArrowType[] values = ArrowType.values();

        for (int i = 0; i < values.length; i++)
        {
            keys.add(values[i].getValue());
        }
        return keys;
    }

    public static final List<ArrowType> getValues()
    {
        return Arrays.asList(ArrowType.values());
    }
}
