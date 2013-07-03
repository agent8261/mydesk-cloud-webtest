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

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.shape.IPrimitive;
import com.emitrom.lienzo.client.core.shape.Node;
import com.emitrom.lienzo.client.core.types.Point2D;

/**
 * AnimationProperty defines what node attribute is modified during a "tweening" animation 
 * and what its ultimate target value is.
 * Several can be animated in parallel, by adding them to an {@link AnimationProperties}.
 * <p>
 * See {@link Properties} for convenience methods to create animations for common node attributes.
 * 
 * @see Properties
 * @see AnimationProperties
 * @see AnimationTweener
 * @see AnimationManager#add(IPrimitive, AnimationTweener, AnimationProperties, int, IAnimationCallback)
 */
public interface AnimationProperty
{
    public Attribute getAttribute();

    public AnimationProperty init(Node<?> node);

    public AnimationProperty apply(Node<?> node, double percent);

    /**
     * Properties provides convenience methods for defining which attributes of an IPrimitive node 
     * will be animated during a "tweening" animation.
     * <p>
     * The resulting {@link AnimationProperty} objects should be grouped together in
     * an {@link AnimationProperties} object.
     * 
     * @see AnimationProperty
     * @see AnimationProperties
     * @see AnimationTweener
     * @see AnimationManager
     */
    public static class Properties
    {
        public static final AnimationProperty X(double x)
        {
            return new DoubleAnimationProperty(x, Attribute.X);
        }

        public static final AnimationProperty Y(double y)
        {
            return new DoubleAnimationProperty(y, Attribute.Y);
        }

        public static final AnimationProperty WIDTH(double wide)
        {
            return new DoubleAnimationProperty(wide, Attribute.WIDTH);
        }

        public static final AnimationProperty HEIGHT(double high)
        {
            return new DoubleAnimationProperty(high, Attribute.HEIGHT);
        }

        public static final DoubleAnimationPropertyConstrained ALPHA(double alpha)
        {
            return new DoubleAnimationPropertyConstrained(alpha, Attribute.ALPHA, 0.0, 1.0);
        }

        public static final AnimationProperty ROTATION(double rotation)
        {
            return new DoubleAnimationProperty(rotation, Attribute.ROTATION);
        }

        public static final DoubleAnimationPropertyConstrained RADIUS(double radius)
        {
            return new DoubleAnimationPropertyConstrained(radius, Attribute.RADIUS, 0.0, Double.MAX_VALUE);
        }

        public static final AnimationProperty ROTATION_DEGREES(double degrees)
        {
            return new DoubleAnimationProperty(degrees * Math.PI / 180, Attribute.ROTATION);
        }

        public static final DoubleAnimationPropertyConstrained STROKE_WIDTH(double stroke)
        {
            return new DoubleAnimationPropertyConstrained(stroke, Attribute.STROKE_WIDTH, 0.0, Double.MAX_VALUE);
        }

        public static final AnimationProperty SCALE(Point2D scale)
        {
            return new Point2DAnimationProperty_1(scale, Attribute.SCALE);
        }

        public static final AnimationProperty SCALE(double scale)
        {
            return new Point2DAnimationProperty_1(new Point2D(scale, scale), Attribute.SCALE);
        }

        public static final AnimationProperty SCALE(double x, double y)
        {
            return new Point2DAnimationProperty_1(new Point2D(x, y), Attribute.SCALE);
        }

        public static final AnimationProperty OFFSET(Point2D scale)
        {
            return new Point2DAnimationProperty_0(scale, Attribute.OFFSET);
        }

        public static final AnimationProperty OFFSET(double value)
        {
            return new Point2DAnimationProperty_0(new Point2D(value, value), Attribute.OFFSET);
        }

        public static final AnimationProperty OFFSET(double x, double y)
        {
            return new Point2DAnimationProperty_0(new Point2D(x, y), Attribute.OFFSET);
        }

        public static final AnimationProperty SHEAR(Point2D shear)
        {
            return new Point2DAnimationProperty_0(shear, Attribute.SHEAR);
        }

        public static final AnimationProperty SHEAR(double value)
        {
            return new Point2DAnimationProperty_0(new Point2D(value, value), Attribute.SHEAR);
        }

        public static final AnimationProperty SHEAR(double x, double y)
        {
            return new Point2DAnimationProperty_0(new Point2D(x, y), Attribute.SHEAR);
        }

        private static class DoubleAnimationProperty implements AnimationProperty
        {
            private double    m_target;

            private double    m_origin;

            private Attribute m_attribute;

            public DoubleAnimationProperty(double target, Attribute attribute)
            {
                m_target = target;

                m_attribute = attribute;
            }

            @Override
            public AnimationProperty init(Node<?> node)
            {
                if (node != null)
                {
                    m_origin = node.getAttributes().getDouble(m_attribute.getProperty());
                }
                return this;
            }

            @Override
            public AnimationProperty apply(Node<?> node, double percent)
            {
                if (node != null)
                {
                    node.getAttributes().putDouble(m_attribute.getProperty(), (m_origin + ((m_target - m_origin) * percent)));
                }

                return this;
            }

            @Override
            public Attribute getAttribute()
            {
                return m_attribute;
            }
        }

        private static class DoubleAnimationPropertyConstrained implements AnimationProperty
        {
            private double          m_origin;

            private double          m_target;

            private final double    m_minval;

            private final double    m_maxval;

            private final Attribute m_attribute;

            public DoubleAnimationPropertyConstrained(double target, Attribute attribute, double minval, double maxval)
            {
                m_target = target;

                m_attribute = attribute;

                m_minval = minval;

                m_maxval = maxval;
            }

            @Override
            public AnimationProperty init(Node<?> node)
            {
                if ((null != node) && (null != m_attribute))
                {
                    m_origin = node.getAttributes().getDouble(m_attribute.getProperty());

                    if (m_origin < m_minval)
                    {
                        m_origin = m_minval;
                    }
                    if (m_origin > m_maxval)
                    {
                        m_origin = m_maxval;
                    }
                    if (m_target < m_minval)
                    {
                        m_target = m_minval;
                    }
                    if (m_target > m_maxval)
                    {
                        m_target = m_maxval;
                    }
                }
                return this;
            }

            @Override
            public AnimationProperty apply(Node<?> node, double percent)
            {
                if ((null != node) && (null != m_attribute))
                {
                    double value = (m_origin + ((m_target - m_origin) * percent));

                    if (value < m_minval)
                    {
                        value = m_minval;
                    }
                    if (value > m_maxval)
                    {
                        value = m_maxval;
                    }
                    node.getAttributes().putDouble(m_attribute.getProperty(), value);
                }
                return this;
            }

            @Override
            public Attribute getAttribute()
            {
                return m_attribute;
            }
        }

        private static class Point2DAnimationProperty_0 implements AnimationProperty
        {
            private Point2D   m_target;

            private Point2D   m_origin;

            private Attribute m_attribute;

            public Point2DAnimationProperty_0(Point2D target, Attribute attribute)
            {
                m_target = target;

                m_attribute = attribute;
            }

            @Override
            public AnimationProperty init(Node<?> node)
            {
                if ((null != node) && (null != m_attribute))
                {
                    m_origin = node.getAttributes().getPoint2D(m_attribute.getProperty());

                    if (null == m_origin)
                    {
                        m_origin = new Point2D(0, 0);
                    }
                }
                return this;
            }

            @Override
            public AnimationProperty apply(Node<?> node, double percent)
            {
                if ((null != node) && (null != m_attribute))
                {
                    double x = m_origin.getX() + ((m_target.getX() - m_origin.getX()) * percent);

                    double y = m_origin.getY() + ((m_target.getY() - m_origin.getY()) * percent);

                    node.getAttributes().putPoint2D(m_attribute.getProperty(), new Point2D(x, y));
                }
                return this;
            }

            @Override
            public Attribute getAttribute()
            {
                return m_attribute;
            }
        }

        private static class Point2DAnimationProperty_1 implements AnimationProperty
        {
            private Point2D   m_target;

            private Point2D   m_origin;

            private Attribute m_attribute;

            public Point2DAnimationProperty_1(Point2D target, Attribute attribute)
            {
                m_target = target;

                m_attribute = attribute;
            }

            @Override
            public AnimationProperty init(Node<?> node)
            {
                if ((null != node) && (null != m_attribute))
                {
                    m_origin = node.getAttributes().getPoint2D(m_attribute.getProperty());

                    if (null == m_origin)
                    {
                        m_origin = new Point2D(1, 1);
                    }
                }
                return this;
            }

            @Override
            public AnimationProperty apply(Node<?> node, double percent)
            {
                if ((null != node) && (null != m_attribute))
                {
                    double x = m_origin.getX() + ((m_target.getX() - m_origin.getX()) * percent);

                    double y = m_origin.getY() + ((m_target.getY() - m_origin.getY()) * percent);

                    node.getAttributes().putPoint2D(m_attribute.getProperty(), new Point2D(x, y));
                }
                return this;
            }

            @Override
            public Attribute getAttribute()
            {
                return m_attribute;
            }
        }
    }
}
