package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;

import edu.umich.imlc.mydesk.cloud.frontend.canvas.DrawableFile;

public class WeSketchFile_GWT implements DrawableFile
{
  private String fileName;
  private String fileID;
  private String file_format_version;
  private ArrayList<Slide_GWT> slides = new ArrayList<Slide_GWT>();

  private int currentSlideIndex = 0;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public final int getTotalSlides()
  {
    return slides.size();
  }
  
  // ---------------------------------------------------------------------------
  
  public void setCurrentSlide(int index)
  {
    assert(index >= 0);
    assert(index < slides.size());
    currentSlideIndex = index;
  }
  
  // ---------------------------------------------------------------------------
  
  public final int getCurrentSlideIndex()
  {
    return currentSlideIndex;
  }
  
  // ---------------------------------------------------------------------------
  
  public boolean hasNextSlide()
  {
    return ((currentSlideIndex + 1) < slides.size());
  }
  
  // ---------------------------------------------------------------------------
  
  public void doNextSlide()
  {
    if(slides.isEmpty())
      return;
    if(currentSlideIndex == (slides.size() - 1))
      return;
    currentSlideIndex++;
  }
  
  // ---------------------------------------------------------------------------
  
  public boolean hasPrevSlide()
  {
    return currentSlideIndex > 0;
  }
  
  // ---------------------------------------------------------------------------
  
  public void doPrevSlide()
  {
    if(currentSlideIndex == 0)
      return;
    currentSlideIndex--;
  }   
  
  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {
    assert(currentSlideIndex >= 0);
    assert(currentSlideIndex < slides.size());
    slides.get(currentSlideIndex).draw(context);
  }
  
  // ---------------------------------------------------------------------------
  
  public WeSketchFile_GWT(String fileID_)
  {
    fileID = fileID_;
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for GWT-RPC serialization.
   */
  public WeSketchFile_GWT()
  {

  }

  // ---------------------------------------------------------------------------

  /**
   * BE VERY CAREFUL ABOUT SETTING THIS. Ideally this should be set in the
   * constructor.
   */
  
  // ---------------------------------------------------------------------------
  
  public void setFileFormatVersion(String version)
  {
    file_format_version = version;
  }
  
  // ---------------------------------------------------------------------------
  
  public String getFileFormatVersion()
  {
    return file_format_version;
  }
  
  // ---------------------------------------------------------------------------
  public void setFileID(String fileID_)
  {
    fileID = fileID_;
  }

  // ---------------------------------------------------------------------------

  public String getFileName()
  {
    return fileName;
  }

  // ---------------------------------------------------------------------------

  public void setFileName(String name_)
  {
    fileName = name_;
  }

  // ---------------------------------------------------------------------------

  public String getFileID()
  {
    return fileID;
  }

  // ---------------------------------------------------------------------------

  public Slide_GWT getSlide(int index)
  {
    return slides.get(index);
  }

  // ---------------------------------------------------------------------------

  /**
   * Returns a copy of the list of slides.
   */
  public List<Slide_GWT> getAllSlides()
  {
    return new ArrayList<Slide_GWT>(slides);
  }

  // ---------------------------------------------------------------------------

  public boolean addSlide(Slide_GWT slide)
  {
    return slides.add(slide);
  }

  // ---------------------------------------------------------------------------

  public void addSlide(int index, Slide_GWT slide)
  {
    slides.add(index, slide);
  }

  // ---------------------------------------------------------------------------

  public Slide_GWT getSlideByID(String id)
  {
    assert (id != null);
    for( Slide_GWT slide : slides )
    {
      if( id.equals(slide.getID()) )
        return slide;
    }// for

    return null;
  }// getSlideByID

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public static WeSketchFile_GWT makeDummyFile(String fileID)
  {
    WeSketchFile_GWT file = new WeSketchFile_GWT(fileID);
    file.setFileName("Dummy File");
    Slide_GWT slide = new Slide_GWT("1a0a04de1-8680-4f74-a4e6-f94c238e5833");
    
    // Event 1
    ClearEvent_GWT clearEvent = new ClearEvent_GWT(1);
    slide.add(clearEvent);
    
    // Event 2
    Path_GWT path = new Path_GWT(makeList());
    DrawPathEvent_GWT pathEvent = new DrawPathEvent_GWT(2);
    pathEvent.path = path;
    pathEvent.setColor(-3242460);
    pathEvent.setStrokeWidth(20);
    slide.add(pathEvent);

    // Event 3
    DrawTextEvent_GWT textEvent = new DrawTextEvent_GWT(3);
    textEvent.color = -15048247;
    textEvent.topLeft = new Point_GWT(52, 993);
    textEvent.size = 193;
    textEvent.font = "Helvetica";
    textEvent.text = "WOMP";
    slide.add(textEvent);

    // Event 4
    DrawRectangleEvent_GWT rectEvent = new DrawRectangleEvent_GWT(4);
    rectEvent.color = -7260208;
    rectEvent.topLeft = new Point_GWT(396, 307);
    rectEvent.bottomRight = new Point_GWT(651, 759);
    slide.add(rectEvent);

    // Event 5
    DrawEllipseEvent_GWT ellEvent = new DrawEllipseEvent_GWT(5);
    ellEvent.setColor(-14693582);
    ellEvent.setBoundingRectangleTopLeft(new Point_GWT(43, 91));
    ellEvent.setBoundingRectangleBottomRight(new Point_GWT(217, 715));
    slide.add(ellEvent);

    // Event 6
    MoveSelectionEvent_GWT moveEvent = new MoveSelectionEvent_GWT(6);
    moveEvent.sourceTopLeft = new Point_GWT(110, 315);
    moveEvent.sourceBottomRight = new Point_GWT(480, 834);
    moveEvent.destinationTopLeft = new Point_GWT(236, 46);
    moveEvent.destinationBottomRight = new Point_GWT(606, 565);
    slide.add(moveEvent);

    // Slide 1
    Slide_GWT rectSlide = new Slide_GWT("rectSlide");
    rectSlide.add(rectEvent);
    //file.addSlide(rectSlide);

    // Slide 2
    Slide_GWT pathSlide = new Slide_GWT("pathSlide");
    pathSlide.add(pathEvent);
    pathSlide.add(rectEvent);
    file.addSlide(pathSlide);
    
    // Slide 3
    Slide_GWT textSlide = new Slide_GWT("textSlide");
    textSlide.add(textEvent);
    textSlide.add(rectEvent);
    file.addSlide(textSlide);
    
    // Slide 4
    Slide_GWT ellipseSlide = new Slide_GWT("ellipseSlide");
    ellipseSlide.add(ellEvent);
    ellipseSlide.add(rectEvent);
    file.addSlide(ellipseSlide);
    
    // Slide 5
    Slide_GWT moveSlide = new Slide_GWT("moveSlide");
    moveSlide.add(moveEvent);
    moveSlide.add(rectEvent);
    file.addSlide(moveSlide);    
    
    // All Events
    file.addSlide(slide);
    return file;
  }// makeDummyFile

  private static List<Point_GWT> makeList()
  {
    List<Point_GWT> points = new ArrayList<Point_GWT>();
    points.add(new Point_GWT(93.75, 924.697));
    points.add(new Point_GWT(94.75, 925.697));
    points.add(new Point_GWT(87.42164, 906.21124));
    points.add(new Point_GWT(86.30124, 857.5628));
    points.add(new Point_GWT(93.89611, 816.12683));
    points.add(new Point_GWT(105.3916, 789.2329));
    points.add(new Point_GWT(124.89165, 766.7555));
    points.add(new Point_GWT(148.3498, 753.81476));
    points.add(new Point_GWT(167.22171, 753.7307));
    points.add(new Point_GWT(194.50867, 769.3977));
    points.add(new Point_GWT(222.54555, 807.43964));
    points.add(new Point_GWT(242.64938, 861.8874));
    points.add(new Point_GWT(249.41864, 902.5972));
    points.add(new Point_GWT(254.7571, 933.58673));
    points.add(new Point_GWT(298.69858, 957.63885));
    points.add(new Point_GWT(361.08047, 949.4085));
    points.add(new Point_GWT(420.42722, 922.21204));
    points.add(new Point_GWT(468.97687, 891.67255));
    points.add(new Point_GWT(489.6952, 871.8458));
    points.add(new Point_GWT(496.40176, 860.2425));
    points.add(new Point_GWT(496.875, 854.09094));
    points.add(new Point_GWT(480.22385, 847.8163));
    points.add(new Point_GWT(429.289, 847.9355));
    points.add(new Point_GWT(369.0378, 857.9756));
    points.add(new Point_GWT(314.47443, 870.02545));
    points.add(new Point_GWT(284.5556, 875.68445));
    points.add(new Point_GWT(263.6483, 873.6705));
    points.add(new Point_GWT(255.10359, 863.7855));
    points.add(new Point_GWT(249.44351, 810.94684));
    points.add(new Point_GWT(259.32303, 744.78613));
    points.add(new Point_GWT(280.87573, 685.565));
    points.add(new Point_GWT(305.25626, 642.9722));
    points.add(new Point_GWT(328.3955, 623.20966));
    points.add(new Point_GWT(349.8347, 614.0538));
    points.add(new Point_GWT(361.88107, 617.88324));
    points.add(new Point_GWT(367.7796, 635.11975));
    points.add(new Point_GWT(362.37097, 686.9036));
    points.add(new Point_GWT(345.4574, 746.0737));
    points.add(new Point_GWT(340.3125, 762.5758));
    points.add(new Point_GWT(332.96774, 785.307));
    points.add(new Point_GWT(350.88544, 823.153));
    points.add(new Point_GWT(380.14673, 831.3499));
    points.add(new Point_GWT(440.50275, 827.5019));
    points.add(new Point_GWT(504.62653, 811.4721));
    points.add(new Point_GWT(566.1751, 793.65454));
    points.add(new Point_GWT(600.4805, 785.8748));
    points.add(new Point_GWT(616.0019, 782.7875));
    points.add(new Point_GWT(620.7808, 782.7878));
    points.add(new Point_GWT(618.4667, 785.03796));
    points.add(new Point_GWT(592.8203, 799.86316));
    points.add(new Point_GWT(535.992, 830.82404));
    points.add(new Point_GWT(497.72433, 846.0504));
    points.add(new Point_GWT(470.78638, 851.53033));
    points.add(new Point_GWT(458.57596, 850.66077));
    points.add(new Point_GWT(453.21213, 845.03986));
    points.add(new Point_GWT(457.18607, 815.37897));
    points.add(new Point_GWT(481.5879, 750.00024));
    points.add(new Point_GWT(512.755, 692.377));
    points.add(new Point_GWT(547.17126, 637.956));
    points.add(new Point_GWT(579.87714, 590.6333));
    points.add(new Point_GWT(604.0942, 552.5903));
    points.add(new Point_GWT(627.9029, 511.60822));
    points.add(new Point_GWT(647.7986, 473.30377));
    points.add(new Point_GWT(657.78534, 440.71802));
    points.add(new Point_GWT(651.32764, 412.09473));
    points.add(new Point_GWT(597.6459, 372.67975));
    points.add(new Point_GWT(540.4587, 358.8421));
    points.add(new Point_GWT(483.36743, 355.02393));
    points.add(new Point_GWT(427.0498, 354.39395));
    points.add(new Point_GWT(386.96277, 354.76266));
    points.add(new Point_GWT(333.53778, 355.0));
    points.add(new Point_GWT(307.08954, 353.49356));
    points.add(new Point_GWT(297.0013, 348.3344));
    points.add(new Point_GWT(293.75, 328.97095));
    points.add(new Point_GWT(305.2334, 265.83765));
    points.add(new Point_GWT(314.9866, 228.37881));
    points.add(new Point_GWT(322.30408, 202.02866));
    points.add(new Point_GWT(324.375, 184.09987));
    points.add(new Point_GWT(320.06168, 173.90746));
    points.add(new Point_GWT(295.43726, 153.4873));
    points.add(new Point_GWT(238.5866, 135.62431));
    points.add(new Point_GWT(181.04306, 127.22237));
    points.add(new Point_GWT(146.23065, 123.74191));
    points.add(new Point_GWT(126.80455, 118.65842));
    points.add(new Point_GWT(117.04226, 112.51602));
    points.add(new Point_GWT(115.44364, 101.52864));
    points.add(new Point_GWT(128.97995, 74.445404));
    points.add(new Point_GWT(152.61378, 52.906242));
    points.add(new Point_GWT(178.56474, 42.01461));
    points.add(new Point_GWT(186.25, 40.15152));
    points.add(new Point_GWT(201.40268, 49.07688));
    points.add(new Point_GWT(212.0503, 100.31386));
    points.add(new Point_GWT(191.98131, 169.23618));
    points.add(new Point_GWT(156.26436, 243.1008));
    points.add(new Point_GWT(120.061806, 319.9643));
    points.add(new Point_GWT(102.59142, 381.33423));
    points.add(new Point_GWT(104.81816, 413.72803));
    points.add(new Point_GWT(122.42511, 440.17145));
    points.add(new Point_GWT(166.6478, 460.56476));
    points.add(new Point_GWT(238.44078, 470.50165));
    points.add(new Point_GWT(319.55225, 464.4267));
    points.add(new Point_GWT(404.01855, 448.52893));
    points.add(new Point_GWT(486.53598, 419.15994));
    points.add(new Point_GWT(557.6705, 390.51126));
    points.add(new Point_GWT(612.3303, 365.99136));
    points.add(new Point_GWT(640.6705, 352.87015));
    points.add(new Point_GWT(663.2318, 337.33362));
    points.add(new Point_GWT(676.5733, 323.68784));
    points.add(new Point_GWT(679.557, 316.8948));
    points.add(new Point_GWT(677.88184, 306.66623));
    points.add(new Point_GWT(664.05115, 281.1941));
    points.add(new Point_GWT(632.6764, 233.97012));
    points.add(new Point_GWT(598.34717, 193.48529));
    points.add(new Point_GWT(575.5401, 169.96646));
    points.add(new Point_GWT(534.32904, 141.03833));
    points.add(new Point_GWT(510.625, 129.54544));
    points.add(new Point_GWT(484.50104, 128.78798));
    points.add(new Point_GWT(396.20847, 126.91986));
    points.add(new Point_GWT(352.52072, 129.33037));
    points.add(new Point_GWT(342.1875, 129.54546));
    points.add(new Point_GWT(338.29456, 129.54546));
    points.add(new Point_GWT(387.14105, 121.44066));
    points.add(new Point_GWT(453.75378, 111.96918));
    points.add(new Point_GWT(511.7908, 108.81824));
    points.add(new Point_GWT(545.1742, 115.441574));
    points.add(new Point_GWT(573.5462, 130.49802));
    points.add(new Point_GWT(588.23285, 165.59587));
    points.add(new Point_GWT(581.435, 249.07785));
    points.add(new Point_GWT(561.35175, 330.199));
    points.add(new Point_GWT(547.4217, 409.6878));
    points.add(new Point_GWT(541.83093, 477.28052));
    points.add(new Point_GWT(539.38324, 538.956));
    points.add(new Point_GWT(542.8066, 597.4415));
    points.add(new Point_GWT(540.361, 656.6034));
    points.add(new Point_GWT(524.727, 711.70123));
    points.add(new Point_GWT(498.3549, 748.777));
    points.add(new Point_GWT(452.72693, 781.7845));
    points.add(new Point_GWT(397.52353, 806.40497));
    points.add(new Point_GWT(360.18036, 811.4466));
    points.add(new Point_GWT(317.43753, 807.37823));
    points.add(new Point_GWT(268.75, 790.1515));
    points.add(new Point_GWT(258.64835, 779.3664));
    points.add(new Point_GWT(214.51085, 701.4391));
    points.add(new Point_GWT(196.17012, 619.85315));
    points.add(new Point_GWT(199.90828, 548.6022));
    points.add(new Point_GWT(221.29631, 479.9737));
    points.add(new Point_GWT(256.21524, 416.10992));
    points.add(new Point_GWT(293.88287, 372.97562));
    points.add(new Point_GWT(316.2204, 355.6233));
    points.add(new Point_GWT(325.50107, 351.2905));
    points.add(new Point_GWT(328.4375, 350.15155));
    points.add(new Point_GWT(282.50012, 399.70575));
    points.add(new Point_GWT(221.05258, 454.17413));
    points.add(new Point_GWT(163.83183, 500.2874));
    points.add(new Point_GWT(120.163246, 535.8941));
    points.add(new Point_GWT(103.886375, 547.8412));
    points.add(new Point_GWT(99.67303, 551.6807));
    points.add(new Point_GWT(97.59575, 553.69507));
    points.add(new Point_GWT(129.48405, 543.59216));
    points.add(new Point_GWT(209.98935, 516.82166));
    points.add(new Point_GWT(293.038, 501.58765));
    points.add(new Point_GWT(360.71613, 501.81744));
    points.add(new Point_GWT(411.89966, 516.2382));
    points.add(new Point_GWT(436.27454, 534.6566));
    points.add(new Point_GWT(452.66653, 566.5621));
    points.add(new Point_GWT(462.90387, 619.77405));
    points.add(new Point_GWT(463.44568, 693.2334));
    points.add(new Point_GWT(464.79465, 766.3593));
    points.add(new Point_GWT(470.83035, 812.4793));
    points.add(new Point_GWT(483.66107, 841.4675));
    points.add(new Point_GWT(507.078, 864.17004));
    points.add(new Point_GWT(550.1164, 879.14886));
    points.add(new Point_GWT(595.46423, 884.01196));
    points.add(new Point_GWT(627.69324, 885.2604));
    points.add(new Point_GWT(649.559, 887.13495));
    points.add(new Point_GWT(657.66376, 889.609));
    points.add(new Point_GWT(661.9749, 892.645));
    points.add(new Point_GWT(663.1953, 896.41675));
    points.add(new Point_GWT(662.43866, 900.2052));
    points.add(new Point_GWT(663.20874, 903.2847));
    points.add(new Point_GWT(681.2048, 898.6583));
    points.add(new Point_GWT(724.65106, 868.9007));
    points.add(new Point_GWT(752.05255, 844.413));
    points.add(new Point_GWT(769.78925, 814.9135));
    points.add(new Point_GWT(779.2437, 785.70013));
    points.add(new Point_GWT(782.8224, 759.7527));
    points.add(new Point_GWT(782.57544, 739.7334));
    points.add(new Point_GWT(776.4149, 712.6734));
    points.add(new Point_GWT(768.7421, 684.0605));
    points.add(new Point_GWT(760.799, 657.7848));
    points.add(new Point_GWT(754.9938, 641.49554));
    points.add(new Point_GWT(745.3657, 627.45465));
    points.add(new Point_GWT(738.58856, 622.7443));
    points.add(new Point_GWT(726.5937, 621.3359));
    points.add(new Point_GWT(709.9413, 624.9054));
    points.add(new Point_GWT(695.03937, 634.315));
    points.add(new Point_GWT(676.9994, 656.9404));
    points.add(new Point_GWT(664.1992, 687.6208));
    points.add(new Point_GWT(655.86194, 722.10626));
    points.add(new Point_GWT(648.0303, 760.30786));
    points.add(new Point_GWT(642.9939, 783.8017));
    points.add(new Point_GWT(638.3681, 807.3314));
    points.add(new Point_GWT(630.7268, 837.0293));
    points.add(new Point_GWT(622.4155, 869.84973));
    points.add(new Point_GWT(610.63025, 902.56305));
    points.add(new Point_GWT(599.0074, 931.7592));
    points.add(new Point_GWT(589.1307, 949.7785));
    points.add(new Point_GWT(582.3315, 959.66235));
    points.add(new Point_GWT(575.357, 966.77515));
    points.add(new Point_GWT(566.1491, 972.1122));
    points.add(new Point_GWT(557.98755, 976.2788));
    points.add(new Point_GWT(548.7537, 980.02014));
    points.add(new Point_GWT(539.73047, 983.16394));
    points.add(new Point_GWT(531.3849, 985.03455));
    points.add(new Point_GWT(524.9326, 985.9092));
    points.add(new Point_GWT(522.1875, 985.9092));
    points.add(new Point_GWT(517.0596, 987.6915));
    points.add(new Point_GWT(515.31573, 988.02966));
    points.add(new Point_GWT(513.7005, 987.7273));
    points.add(new Point_GWT(509.9985, 989.91003));
    points.add(new Point_GWT(509.9985, 989.91003));
    return points;
  }
}// class
