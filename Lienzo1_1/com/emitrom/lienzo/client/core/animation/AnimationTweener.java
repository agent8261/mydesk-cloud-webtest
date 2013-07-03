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

/**
 * The AnimationTweener defines how the attributes change over time.
 * <p>
 * E.g. AnimationTweener.LINEAR applies a linear change from the original value to the resulting value. 
 * E.g. if the initial value is 100 and the target value is 200, the intermediate value would be 
 * <code>100 + p * (200 - 100)</code>, where p is a value that grows from 0 to 1 over the duration of the animation.
 * <p>
 * The AnimationTweener class provides several predefined tweeners, but you can customize them with TweenerBuilder. 
 * Or you can create you own AnimationTweener from scratch.
 * 
 * @see AnimationManager#add(com.emitrom.lienzo.client.core.shape.IPrimitive, AnimationTweener, AnimationProperties, int, IAnimationCallback)
 * @see com.emitrom.lienzo.client.core.shape.IPrimitive#animate(AnimationTweener, AnimationProperties, int)
 * @see com.emitrom.lienzo.client.core.shape.IPrimitive#animate(AnimationTweener, AnimationProperties, int, IAnimationCallback)
 */
public interface AnimationTweener
{
    public static final AnimationTweener LINEAR      = TweenerBuilder.makeLINEAR();

    public static final AnimationTweener EASE_IN     = TweenerBuilder.makeEASE_IN(2);

    public static final AnimationTweener EASE_OUT    = TweenerBuilder.makeEASE_OUT(2);

    public static final AnimationTweener EASE_IN_OUT = TweenerBuilder.makeEASE_IN_OUT();

    public static final AnimationTweener ELASTIC     = TweenerBuilder.makeELASTIC(3);

    public double tween(double percent);

    public static final class TweenerBuilder
    {
        public static final AnimationTweener makeLINEAR()
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return percent;
                }
            };
        }

        public static final AnimationTweener makeEASE_IN(final double strength)
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return Math.pow(percent, strength * 2.0);
                }
            };
        }

        public static final AnimationTweener makeEASE_OUT(final double strength)
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return (1.0 - Math.pow(1.0 - percent, strength * 2.0));
                }
            };
        }

        public static final AnimationTweener makeEASE_IN_OUT()
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return (percent - Math.sin(percent * 2.0 * Math.PI) / (2.0 * Math.PI));
                }
            };
        }

        public static final AnimationTweener makeELASTIC(final int passes)
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return (((1.0 - Math.cos(percent * Math.PI * passes)) * (1.0 - percent)) + percent);
                }
            };
        }
    }
}
