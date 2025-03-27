package hw1;

/**
 * Model of a car stereo is created.
 * @author adinhuric
 */
public class CarStereo {
	
	/**
	 * Amount in which the volume will be increased or decreased by.
	 */
	public static final double VOLUME_STEP = 0.16;
	/**
	 * That value of the volume.
	 */
	private double volume;
	/**
	 * Number of stations the car stereo has.
	 */
	private int stations;
	/**
	 * The maximum frequency that the car stereo can have.
	 */
	private double maxFrequency;
	/**
	 * The minimum frequency that the car stereo can have.
	 */
	private double minFrequency;
	/**
	 * The preset value.
	 */
	private double preset;
	/**
	 * The current frequency of the car stereo.
	 */
	private double currentFrequency;
	/**
	 * The amount of stations available in the car stereo.
	 */
	private int stationNumber;
	/**
	 * The value of the intervals for the tuner.
	 */
	private int interval; 
	
	/**
	 * Constructs a car stereo with a minimum frequency, a maximum frequency, and a number of stations.
	 * @param givenMinFrequency, givenMaxFrequency, givenNumStations
	 */
	public CarStereo (double givenMinFrequency, double givenMaxFrequency, int givenNumStations)
	{
		minFrequency = givenMinFrequency;
		maxFrequency = givenMaxFrequency;
		stations = givenNumStations;
		volume = 0.5;
		currentFrequency = givenMinFrequency;
		preset = 0;
		interval = (int) (maxFrequency - minFrequency) / stations;
	}
	
	/**
	 * Retrieves the current volume of the car stereo.
	 * @return 
	 * current volume of the car stereo
	 */
	public double getVolume()
	{
		volume = Math.min(volume, 1.0);
		volume = Math.max(volume, 0.0);
		return volume;
	}
	
	/**
	 * Increases the volume of the car stereo.
	 */
	public void louder()
	{
		volume = Math.min(volume, 1.0);
		volume = Math.max(volume, 0.0);
		volume += VOLUME_STEP;
	}
	
	/**
	 * Lowers the volume of the car stereo.
	 */
	public void quieter()
	{
		volume = Math.min(volume, 1.0);
		volume = Math.max(volume, 0.0);
		volume -= VOLUME_STEP;
	}
	
	/**
	 * Returns the current frequency of the car stereo.
	 * @return
	 * current frequency of the car stereo
	 */
	public double getTuner()
	{
		return currentFrequency;
	}
	
	/**
	 * Input a number and it sets the frequency.
	 * @param givenFrequency
	 */
	public void setTuner (double givenFrequency)
	{
		currentFrequency = Math.max(givenFrequency, minFrequency);
		currentFrequency = Math.min(currentFrequency, maxFrequency);
		
		stationNumber = (int) ((currentFrequency - minFrequency) / interval);
		stationNumber = Math.max(0, stationNumber);
		stationNumber = Math.min(stationNumber, stations - 1);
	}
	
	/**
	 * Turns the dial of the radio which allows you to either increase or decrease the frequency.
	 * @param degrees
	 */
	public void turnDial(double degrees)
	{
		currentFrequency = Math.min(currentFrequency + ((degrees / 360)* (maxFrequency - minFrequency)), maxFrequency);
		currentFrequency = Math.max(currentFrequency, minFrequency);
	}
	
	/**
	 * Set the frequency from setting the station number.
	 * @param stationNumber
	 */
	public void setTunerFromStationNumber(int stationNumber)
	{
		stationNumber = Math.min(stationNumber, (stations - 1));
		stationNumber = Math.max(0, stationNumber);
		currentFrequency = (minFrequency) + (interval * stationNumber) + (interval / 2);
	}
	
	/**
	 * Allows you to enter a frequency and find a station from that frequency.
	 * @return
	 * station from that frequency
	 */
	public int findStationNumber()
	{
		currentFrequency = Math.min(currentFrequency, maxFrequency);
		currentFrequency = Math.max(currentFrequency, minFrequency);
		stationNumber = (int) ((currentFrequency - minFrequency) / interval);
		stationNumber = Math.max(0, stationNumber);
		stationNumber = Math.min(stationNumber, stations - 1);
		return stationNumber;
	}
	
	/**
	 * Decreases the station number by one.
	 */
	public void seekDown()
	{
		stationNumber = stationNumber - 1;
		stationNumber = stationNumber % stations;
		currentFrequency = (stationNumber * interval) + minFrequency + (interval / 2);
	}
	
	/**
	 * Increases the station number by one.
	 */
	public void seekUp()
	{
		stationNumber = stationNumber + 1;
		stationNumber = stationNumber % stations;
		currentFrequency = (stationNumber * interval) + minFrequency + (interval / 2);
	}
	
	/**
	 * Allows you to enter a value and make a preset of that value.
	 */
	public void savePreset()
	{
		preset = stationNumber;
	}
	
	/**
	 * Sends you toe the frequency of that preset.
	 */
	public void goToPreset()
	{
		currentFrequency = (preset * interval) + minFrequency + (interval / 2);
	}
}
