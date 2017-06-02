/**
 * 
 */


import java.util.HashMap;
import java.util.Map;

/**
 * @author Tanatsigwa Hungwe <hungweta@bu.edu>
 * @since Fall 2016
 */
public class TestCases extends CyclicIterator<Map<String, Angled>> {

  Map<String, Angled> stop() {
    return this.stop;
  }

  private final Map<String, Angled> stop;

  @SuppressWarnings("unchecked")
  TestCases() {
    this.stop = new HashMap<String, Angled>();
    final Map<String, Angled> one = new HashMap<String, Angled>();
    final Map<String, Angled> two = new HashMap<String, Angled>();
    final Map<String, Angled> three = new HashMap<String, Angled>();
    final Map<String, Angled> four = new HashMap<String, Angled>();
    final Map<String, Angled> five = new HashMap<String, Angled>();
    super.add(stop, one, two, three, four, five);

    // the following do not change in any of the test cases
    // Head and Body
    stop.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    two.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    three.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    four.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    five.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    
    stop.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    two.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    three.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    four.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    five.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    
    // Stop Test case - Default Legs are flat
    stop.put(PA2.PINKY_DISTAL_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.PINKY_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.PINKY_THORAX_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.RING_DISTAL_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.RING_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.RING_THORAX_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.MIDDLE_DISTAL_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.MIDDLE_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.MIDDLE_THORAX_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.INDEX_DISTAL_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.INDEX_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.INDEX_THORAX_NAME, new BaseAngled(180, 0, 0));
    stop.put(PA2.THUMB_DISTAL_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.THUMB_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.THUMB_THORAX_NAME, new BaseAngled(180, 0, 0));
    stop.put(PA2.THUMB_DISTAL_NAME2, new BaseAngled(0, 0, 0));
    stop.put(PA2.THUMB_MIDDLE_NAME2, new BaseAngled(0, 0, 0));
    stop.put(PA2.THUMB_THORAX_NAME2, new BaseAngled(180, 0, 0));
    stop.put(PA2.WING_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.WING_NAME2, new BaseAngled(0, 0, 0));
    stop.put(PA2.WING_JOINT_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.WING_JOINT_NAME2, new BaseAngled(0, 0, 0));
       
    // Case 1 - Stand
    one.put(PA2.PINKY_DISTAL_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.PINKY_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.PINKY_THORAX_NAME, new BaseAngled(60, 0, 0));
    one.put(PA2.RING_DISTAL_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.RING_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.RING_THORAX_NAME, new BaseAngled(60, 0, 0));
    one.put(PA2.MIDDLE_DISTAL_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.MIDDLE_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.MIDDLE_THORAX_NAME, new BaseAngled(60, 0, 0));
    one.put(PA2.INDEX_DISTAL_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.INDEX_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.INDEX_THORAX_NAME, new BaseAngled(-60, 180, 180));
    one.put(PA2.THUMB_DISTAL_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.THUMB_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.THUMB_THORAX_NAME, new BaseAngled(-60, 180, 180));
    one.put(PA2.THUMB_DISTAL_NAME2, new BaseAngled(0, 0, 0));
    one.put(PA2.THUMB_MIDDLE_NAME2, new BaseAngled(0, 0, 0));
    one.put(PA2.THUMB_THORAX_NAME2, new BaseAngled(-60, 180, 180));
    one.put(PA2.WING_NAME, new BaseAngled(0, 0, 0));
    one.put(PA2.WING_NAME2, new BaseAngled(0, 0, 0));
    one.put(PA2.WING_JOINT_NAME, new BaseAngled(0, 0, 6));
    one.put(PA2.WING_JOINT_NAME2, new BaseAngled(0, 0, 6));
    
    // Case 2 - Right Legs Up
    two.put(PA2.PINKY_DISTAL_NAME, new BaseAngled(3, 0, 0));
    two.put(PA2.PINKY_MIDDLE_NAME, new BaseAngled(4, 0, 0));
    two.put(PA2.PINKY_THORAX_NAME, new BaseAngled(75, 10, 0));
    two.put(PA2.RING_DISTAL_NAME, new BaseAngled(1, 0, 0));
    two.put(PA2.RING_MIDDLE_NAME, new BaseAngled(2, 0, 0));
    two.put(PA2.RING_THORAX_NAME, new BaseAngled(75, 12, 0));
    two.put(PA2.MIDDLE_DISTAL_NAME, new BaseAngled(6, 0, 0));
    two.put(PA2.MIDDLE_MIDDLE_NAME, new BaseAngled(1, 0, 0));
    two.put(PA2.MIDDLE_THORAX_NAME, new BaseAngled(75, 9, 0));
    two.put(PA2.INDEX_DISTAL_NAME, new BaseAngled(0, 0, 0));
    two.put(PA2.INDEX_MIDDLE_NAME, new BaseAngled(-22, 0, 0));
    two.put(PA2.INDEX_THORAX_NAME, new BaseAngled(10, 196, 180));
    two.put(PA2.THUMB_DISTAL_NAME, new BaseAngled(0, 0, 0));
    two.put(PA2.THUMB_MIDDLE_NAME, new BaseAngled(-20, 0, 0));
    two.put(PA2.THUMB_THORAX_NAME, new BaseAngled(10, 196, 180));
    two.put(PA2.THUMB_DISTAL_NAME2, new BaseAngled(0, 0, 0));
    two.put(PA2.THUMB_MIDDLE_NAME2, new BaseAngled(-18, 0, 0));
    two.put(PA2.THUMB_THORAX_NAME2, new BaseAngled(8, 196, 180));
    two.put(PA2.WING_NAME, new BaseAngled(0, 0, 0));
    two.put(PA2.WING_NAME2, new BaseAngled(0, 0, 0));
    two.put(PA2.WING_JOINT_NAME, new BaseAngled(0, -5, 5));
    two.put(PA2.WING_JOINT_NAME2, new BaseAngled(0, -5, 5));
    
    // Case 3 - Sleep
    three.put(PA2.PINKY_DISTAL_NAME, new BaseAngled(8, 0, 0));
    three.put(PA2.PINKY_MIDDLE_NAME, new BaseAngled(70, 0, 0));
    three.put(PA2.PINKY_THORAX_NAME, new BaseAngled(80, -16, 0));
    three.put(PA2.RING_DISTAL_NAME, new BaseAngled(8, 0, 0));
    three.put(PA2.RING_MIDDLE_NAME, new BaseAngled(70, 0, 0));
    three.put(PA2.RING_THORAX_NAME, new BaseAngled(80, -16, 0));
    three.put(PA2.MIDDLE_DISTAL_NAME, new BaseAngled(8, 0, 0));
    three.put(PA2.MIDDLE_MIDDLE_NAME, new BaseAngled(70, 0, 0));
    three.put(PA2.MIDDLE_THORAX_NAME, new BaseAngled(80, -16, 0));
    three.put(PA2.INDEX_DISTAL_NAME, new BaseAngled(-8, 0, 0));
    three.put(PA2.INDEX_MIDDLE_NAME, new BaseAngled(-70, 0, 0));
    three.put(PA2.INDEX_THORAX_NAME, new BaseAngled(-80, 170, 180));
    three.put(PA2.THUMB_DISTAL_NAME, new BaseAngled(-8, 0, 0));
    three.put(PA2.THUMB_MIDDLE_NAME, new BaseAngled(-70, 0, 0));
    three.put(PA2.THUMB_THORAX_NAME, new BaseAngled(-80, 170, 180));
    three.put(PA2.THUMB_DISTAL_NAME2, new BaseAngled(-8, 0, 0));
    three.put(PA2.THUMB_MIDDLE_NAME2, new BaseAngled(-70, 0, 0));
    three.put(PA2.THUMB_THORAX_NAME2, new BaseAngled(-80, 170, 180));
    three.put(PA2.WING_NAME, new BaseAngled(5, 0, 0));
    three.put(PA2.WING_NAME2, new BaseAngled(5, 0, 0));
    three.put(PA2.WING_JOINT_NAME, new BaseAngled(0, -9, 0));
    three.put(PA2.WING_JOINT_NAME2, new BaseAngled(0, -9, 0));
    
    // Case 4 - Left legs up
    four.put(PA2.PINKY_DISTAL_NAME, new BaseAngled(8, 0, 0));
    four.put(PA2.PINKY_MIDDLE_NAME, new BaseAngled(13, 0, 0));
    four.put(PA2.PINKY_THORAX_NAME, new BaseAngled(-10, -12, 0));
    four.put(PA2.RING_DISTAL_NAME, new BaseAngled(8, 0, 0));
    four.put(PA2.RING_MIDDLE_NAME, new BaseAngled(14, 0, 0));
    four.put(PA2.RING_THORAX_NAME, new BaseAngled(-9, -12, 0));
    four.put(PA2.MIDDLE_DISTAL_NAME, new BaseAngled(8, 0, 0));
    four.put(PA2.MIDDLE_MIDDLE_NAME, new BaseAngled(15, 0, 0));
    four.put(PA2.MIDDLE_THORAX_NAME, new BaseAngled(-9, -12, 0));
    four.put(PA2.INDEX_DISTAL_NAME, new BaseAngled(-5, 0, 0));
    four.put(PA2.INDEX_MIDDLE_NAME, new BaseAngled(-20, 0, 0));
    four.put(PA2.INDEX_THORAX_NAME, new BaseAngled(-75, 180, 180));
    four.put(PA2.THUMB_DISTAL_NAME, new BaseAngled(-5, 0, 0));
    four.put(PA2.THUMB_MIDDLE_NAME, new BaseAngled(-20, 0, 0));
    four.put(PA2.THUMB_THORAX_NAME, new BaseAngled(-75, 180, 180));
    four.put(PA2.THUMB_DISTAL_NAME2, new BaseAngled(-5, 0, 0));
    four.put(PA2.THUMB_MIDDLE_NAME2, new BaseAngled(-20, 0, 0));
    four.put(PA2.THUMB_THORAX_NAME2, new BaseAngled(-75, 180, 180));
    four.put(PA2.WING_NAME, new BaseAngled(0, 0, 0));
    four.put(PA2.WING_NAME2, new BaseAngled(0, 0, 0));
    four.put(PA2.WING_JOINT_NAME, new BaseAngled(0, -5, 4));
    four.put(PA2.WING_JOINT_NAME2, new BaseAngled(0, 5, 4));
       
    // Case 5 - Fly
    five.put(PA2.PINKY_DISTAL_NAME, new BaseAngled(8, 0, 0));
    five.put(PA2.PINKY_MIDDLE_NAME, new BaseAngled(25, 0, 0));
    five.put(PA2.PINKY_THORAX_NAME, new BaseAngled(80, 0, 0));   
    five.put(PA2.RING_DISTAL_NAME, new BaseAngled(8, 0, 0));
    five.put(PA2.RING_MIDDLE_NAME, new BaseAngled(25, 0, 0));
    five.put(PA2.RING_THORAX_NAME, new BaseAngled(80, 0, 0));
    five.put(PA2.MIDDLE_DISTAL_NAME, new BaseAngled(8, 0, 0));
    five.put(PA2.MIDDLE_MIDDLE_NAME, new BaseAngled(25, 0, 0));
    five.put(PA2.MIDDLE_THORAX_NAME, new BaseAngled(80, 0, 0));  
    five.put(PA2.INDEX_DISTAL_NAME, new BaseAngled(-8, 0, 0));
    five.put(PA2.INDEX_MIDDLE_NAME, new BaseAngled(-25, 0, 0));
    five.put(PA2.INDEX_THORAX_NAME, new BaseAngled(-80, 180, 180));
    five.put(PA2.THUMB_DISTAL_NAME, new BaseAngled(-8, 0, 0));
    five.put(PA2.THUMB_MIDDLE_NAME, new BaseAngled(-25, 0, 0));
    five.put(PA2.THUMB_THORAX_NAME, new BaseAngled(-80, 180, 180));
    five.put(PA2.THUMB_DISTAL_NAME2, new BaseAngled(-8, 0, 0));
    five.put(PA2.THUMB_MIDDLE_NAME2, new BaseAngled(-25, 0, 0));
    five.put(PA2.THUMB_THORAX_NAME2, new BaseAngled(-80, 180, 180));
    five.put(PA2.WING_NAME, new BaseAngled(5, 0, 0));
    five.put(PA2.WING_NAME2, new BaseAngled(5, 0, 0));
    five.put(PA2.WING_JOINT_NAME, new BaseAngled(0, -5, 21));
    five.put(PA2.WING_JOINT_NAME2, new BaseAngled(0, 5, 21)); 
  }
}
