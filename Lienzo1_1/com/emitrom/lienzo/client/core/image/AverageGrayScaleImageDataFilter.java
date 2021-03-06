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

package com.emitrom.lienzo.client.core.image;

import com.emitrom.lienzo.client.core.types.ImageData;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;

/**
 * A class that allows for easy creation of Gray Scale Filters.
 */
public class AverageGrayScaleImageDataFilter implements ImageDataFilter
{
    public static final AverageGrayScaleImageDataFilter INSTANCE = new AverageGrayScaleImageDataFilter();

    @Override
    public ImageData filter(ImageData source)
    {
        if (null == source)
        {
            return null;
        }
        final int length = ((source.getWidth() * source.getHeight()) * PIXEL_SZ);

        source = source.copy();

        final CanvasPixelArray data = source.getData();

        if (null == data)
        {
            return source;
        }
        for (int i = 0; i < length; i += PIXEL_SZ)
        {
            int v = (int) ((((double) (data.get(i + R_OFFSET) + data.get(i + G_OFFSET) + data.get(i + B_OFFSET))) / 3.0) + 0.5);

            data.set(i + R_OFFSET, v);

            data.set(i + G_OFFSET, v);

            data.set(i + B_OFFSET, v);
        }
        return source;
    }
}
