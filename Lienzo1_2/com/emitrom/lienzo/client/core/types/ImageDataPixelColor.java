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

package com.emitrom.lienzo.client.core.types;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A simple Red-Blue-Green-Alpha color representation.
 */
public final class ImageDataPixelColor extends JavaScriptObject
{
    protected ImageDataPixelColor()
    {

    }

    public final native int getR()
    /*-{
		return this[0];
    }-*/;

    public final native int getG()
    /*-{
		return this[1]
    }-*/;

    public final native int getB()
    /*-{
		return this[2];
    }-*/;

    public final native int getA()
    /*-{
		return this[3];
    }-*/;

    public final native String toBrowserRGB()
    /*-{
		return "rgb(" + this[0] + "," + this[1] + "," + this[2] + ")";
    }-*/;

    public final native String toBrowserRGBA()
    /*-{
		return "rgba(" + this[0] + "," + this[1] + "," + this[2] + ","
				+ this[3] + ")";
    }-*/;
}
