package com.diana.io.deliveroo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * Represents a type of field to be used in a Cron expression and the corresponding allowed range of values.
 * </p>
 *
 * @author Diana Araujo
 */
public enum CronFieldType
{
    /**
     * The {@code Minute} field type with a value range of [0,59].
     */
    MINUTE("minute", 0, 59, 0), //$NON-NLS-1$

    /**
     * The {@code Hour} field type with a value range of [0,23].
     */
    HOUR("hour", 0, 23, 1), //$NON-NLS-1$

    /**
     * The {@code Day Of Month} field type with a value range of [1,31].
     */
    DAY_OF_MONTH("day of month", 1, 31, 2), //$NON-NLS-1$

    /**
     * The {@code Month} field type with a value range of [1,12].
     */
    MONTH("month", 1, 12, 3), //$NON-NLS-1$

    /**
     * The {@code Day Of Week} field type with a value range of [1,7].
     */
    DAY_OF_WEEK("day of week", 1, 7, 4); //$NON-NLS-1$

    private String display;
    private int minValue;
    private int maxValue;
    private int index;

    private CronFieldType(String display, int minValue, int maxValue, int index)
    {
        this.display = display;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.index = index;
    }

    /**
     * @return the field display
     */
    public String getDisplay()
    {
        return display;
    }

    /**
     * @return the position in which the field type should be positioned in the string to parse.
     */
    public int getIndex()
    {
        return index;
    }
    
    public static CronFieldType getFieldTypeByIndex(int index) 
    {
        for(CronFieldType value:CronFieldType.values()) 
        {
            if (value.index==index) 
            {
                return value;
            }
        }
        return null;
    }

    /**
     * @param value
     *            an integer value
     * @return {@code true} if the value is within the range of the cron field
     */
    public boolean isValueInRange(int value)
    {
        return value >= minValue && value <= maxValue;
    }

    /**
     * @return all numbers available in this cron field.
     */
    public List<Integer> getAllValues()
    {
        return IntStream.rangeClosed(minValue, maxValue).boxed().collect(Collectors.toList());
    }

    /**
     * @param start
     *            the start. If the start is less than the minimum value of the cron field will be used
     * @param end
     *            the end. If the end value is less than the maximum value of the cron field will be used
     * @return
     */
    public List<Integer> getAllValuesInRange(int start, int end)
    {
        if (start < minValue)
        {
            start = minValue;
        }
        if (end > maxValue)
        {
            end = maxValue;
        }
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    /**
     * @param frequency
     *            the frequency to retrieve the values within the cron field
     * @return a list of integers matching the frequency value;
     */
    public List<Integer> getValuesByFrequency(int frequency)
    {
        List<Integer> valuesToAdd = new ArrayList<>();
        int value = minValue;
        while (value <= maxValue)
        {
            valuesToAdd.add(value);
            value += frequency;
        }
        return valuesToAdd;
    }
}
