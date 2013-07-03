
package com.emitrom.lienzo.client.core.shape.path;

import com.emitrom.lienzo.client.core.types.Point2DArray;
import com.google.gwt.core.client.JavaScriptObject;

public class PathPart
{
    private final PathPartJSO m_jso;

    protected PathPart(PathPartJSO jso)
    {
        m_jso = jso;
    }

    public PathPartJSO getJSO()
    {
        return m_jso;
    }

    public PathPartType getType()
    {
        return PathPartType.lookup(m_jso.getType());
    }

    public static class PathPartJSO extends JavaScriptObject
    {
        protected PathPartJSO()
        {
            
        }
        
        public final native String getType()
        /*-{
			return this.type;
        }-*/;

        public static final PathPartJSO makePathPartPoints(PathPartType type, Point2DArray points)
        {
            return makePathPart(type.getValue(), points.getJSO());
        }

        public static final PathPartJSO makePathPart(PathPartType type, JavaScriptObject data)
        {
            return makePathPart(type.getValue(), data);
        }

        public static final PathPartJSO makePathPart(PathPartType type)
        {
            return makePathPart(type.getValue());
        }

        private static final native PathPartJSO makePathPart(String tval)
        /*-{
			return {
				type : tval
			};
        }-*/;

        private static final native PathPartJSO makePathPart(String tval, JavaScriptObject dval)
        /*-{
			return {
				type : tval,
				data : dval
			};
        }-*/;
    }
}
