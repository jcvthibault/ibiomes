package edu.utah.bmi.ibiomes.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.web.domain.Method;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Stochastic dynamics method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="method")
public class LangevinDynamicsMethod extends MDMethod
{
	private long randomSeed = -1;
	private double collisionFrequency;
	
	public LangevinDynamicsMethod()
	{
		super();
		this.name = Method.METHOD_LANGEVIN_DYNAMICS;
	}
	
	public LangevinDynamicsMethod(MetadataAVUList metadata)
	{
		super(metadata);
		this.name = Method.METHOD_LANGEVIN_DYNAMICS;
		
		String collFreqStr = metadata.getValue(MethodMetadata.LANGEVIN_COLLISION_FREQUENCY);
		if (collFreqStr!=null && collFreqStr.length()>0)
		{
			try{
				this.collisionFrequency = Double.parseDouble(collFreqStr);
			}
			catch(NumberFormatException e){
				
			}
		}
		String randomSeedStr = metadata.getValue(MethodMetadata.LANGEVIN_RANDOM_SEED);
		if (randomSeedStr!=null && randomSeedStr.length()>0)
		{
			try{
				this.randomSeed = Long.parseLong(randomSeedStr);
			}
			catch(NumberFormatException e){
				
			}
		}
	}
	
	/**
	 * Get Langevin collision frequency
	 * @return Langevin collision frequency
	 */
	public double getCollisionFrequency() {
		return collisionFrequency;
	}

	/**
	 * Set Langevin collision frequency
	 * @param collisionFrequency Langevin collision frequency
	 */
	public void setCollisionFrequency(double collisionFrequency) {
		this.collisionFrequency = collisionFrequency;
	}
	
	/**
	 * Get random seed for thermostat initialization
	 * @return Random seed for thermostat initialization
	 */
	public long getRandomSeed() {
		return randomSeed;
	}

	/**
	 * Set random seed for thermostat initialization
	 * @param randomSeed Random seed for thermostat initialization
	 */
	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
}
