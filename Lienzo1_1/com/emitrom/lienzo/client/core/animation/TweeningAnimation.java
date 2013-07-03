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

package com.emitrom.lienzo.client.core.animation;

import java.util.List;

import com.emitrom.lienzo.client.core.shape.Layer;
import com.emitrom.lienzo.client.core.shape.Node;

public class TweeningAnimation extends TimedAnimation implements ILayerBatchedAnimation
{
    private final AnimationTweener    m_tweener;

    private final AnimationProperties m_properties;

    public TweeningAnimation(Node<?> node, AnimationTweener tweener, AnimationProperties properties, double duration, IAnimationCallback callback)
    {
        super(duration, callback);

        setNode(node);

        m_tweener = tweener;

        m_properties = properties;
    }

    @Override
    public IAnimation doStart()
    {
        for (AnimationProperty property : m_properties.getProperties())
        {
            property.init(getNode());
        }
        apply(0.0);

        return super.doStart();
    }

    @Override
    public IAnimation doFrame()
    {
        apply(getPercent());

        return super.doFrame();
    }

    @Override
    public IAnimation doClose()
    {
        apply(1.0);

        return super.doClose();
    }

    private void apply(double percent)
    {
        if (null != m_tweener)
        {
            percent = m_tweener.tween(percent);
        }
        if (null != m_properties)
        {
            List<AnimationProperty> list = m_properties.getProperties();

            if (null != list)
            {
                int size = list.size();

                if (size > 0)
                {
                    Node<?> node = getNode();

                    for (int i = 0; i < size; i++)
                    {
                        list.get(i).apply(node, percent);
                    }
                    scheduleBatchedRedraw(node.getLayer());
                }
            }
        }
    }

    @Override
    public void scheduleBatchedRedraw(Layer layer)
    {
        LayerBatchedAnimationRedrawManager.get().scheduleBatchedRedraw(layer);
    }
}
