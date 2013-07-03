
package com.emitrom.lienzo.client.core.shape.path;

import com.emitrom.lienzo.shared.core.types.EnumWithValue;

public enum PathPartType implements EnumWithValue
{
    m("m"), M("M"), l("l"), L("L"), z("z"), a("a"), A("A"), q("q"), Q("Q"), UNKNOWN("unknown");

    private final String m_value;

    private PathPartType(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }

    public static final PathPartType lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            PathPartType[] values = PathPartType.values();

            for (int i = 0; i < values.length; i++)
            {
                PathPartType value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return UNKNOWN;
    }
}
