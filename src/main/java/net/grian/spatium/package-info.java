/**
 * Contains the Minecraft 3D-Library for grian.net.
 * 
 * <br>The package follows a set of consistent rules:
 * <ul>
 *   <li>every object is mutable</li>
 *   
 *   <li>numeric values are stored using {@code float}</li>
 *   
 *   <li>Objects should be created using static methods inside their own
 *       interfaces. F.e. {@code Vector.fromXYZ(float, float, float)}</li>
 *       
 *   <li>Comples objects such as {@code Ray} or {@code AxisAlignedBB} do not
 *       store the objects they are constructed from. When using methods such
 *       as {@code AxisAlignedBB.getMin()} the returned object will be a new
 *       object which's mutation has no effect on the object it originated from
 *       .</li>
 * </ul>
 * 
 * @author Jan "Headaxe" Schultke
 * @see net.grian.api.Ray
 * @see net.grian.api.AxisAlignedBB
 */
package net.grian.spatium;