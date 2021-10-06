package com.diana.io.deliveroo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * A chrono field parser.
 * </p>
 *
 * @author Diana Araujo
 */
public class CronFieldParser
{
    private static final String SEPARATOR_SPACE = " "; //$NON-NLS-1$

    private CronFieldType cronoFieldType;
    private String stringToParse;
    private List<Integer> numericValuesParsed = new ArrayList<>();

    /**
     * @param cronFieldType
     * @param stringToParse
     */
    public CronFieldParser(CronFieldType cronFieldType, String stringToParse)
    {
        this.cronoFieldType = cronFieldType;
        this.stringToParse = stringToParse;
    }

    /**
     * Parses the initial string for the corresponding cron type and return a unique/sorted items.
     * 
     * @return a unique set of parsed integers.
     */
    public List<Integer> parse()
    {
        Set<Integer> uniqueValues = new HashSet<>(parse(stringToParse));
        numericValuesParsed = new ArrayList<>(uniqueValues);
        Collections.sort(numericValuesParsed);
        return numericValuesParsed;
    }

    /**
     * Prints the type of Cron field and the cron frequency if it has been parsed.
     */
    public void print()
    {
        String formattedChrono = String.format("%" + -14 + "s", cronoFieldType.getDisplay());
        System.out.println(formattedChrono
                + numericValuesParsed.stream().map(x -> x.toString()).collect(Collectors.joining(SEPARATOR_SPACE)));
    }

    private List<Integer> parse(String toParse)
    {
        if (isNumeric(toParse))
        {
            Integer parsedInteger = Integer.valueOf(toParse);
            if (cronoFieldType.isValueInRange(parsedInteger))
            {
                return Arrays.asList(parsedInteger);
            }
            return Collections.emptyList();
        }
        if (CronFieldOperation.ALL.getSymbol().equals(toParse))
        {
            return cronoFieldType.getAllValues();
        }

        List<Integer> parsedValues = new ArrayList<>();
        if (CronFieldOperation.LIST.contains(toParse))
        {
            parsedValues.addAll(handleListOperation(toParse));
        }
        else if (CronFieldOperation.RANGE.contains(toParse))
        {
            parsedValues.addAll(handleRangeOperation(toParse));
        }
        else if (CronFieldOperation.FREQUENCY.contains(toParse))
        {
            parsedValues.addAll(handleFrequencyOperation(toParse));
        }
        else
        {
            throw new IllegalArgumentException("Invalid parser identifier:" + toParse);
        }

        return parsedValues;
    }

    private List<Integer> handleListOperation(String toParse)
    {
        String[] parsedExpressions = toParse.split(CronFieldOperation.LIST.getRegex());
        return Arrays.stream(parsedExpressions).map(x -> parse(x)).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<Integer> handleRangeOperation(String toParse) throws NumberFormatException
    {
        String[] rangeValues = toParse.split(CronFieldOperation.RANGE.getRegex());
        if (rangeValues.length != 2)
        {
            throw new IllegalArgumentException("Unable to parse Range symbol due to unexpected characters.");
        }
        else if (!isNumeric(rangeValues[0]) || !isNumeric(rangeValues[1]))
        {
            throw new IllegalArgumentException("Invalid arguments to get range value:" + rangeValues);
        }
        return cronoFieldType.getAllValuesInRange(Integer.parseInt(rangeValues[0]), Integer.parseInt(rangeValues[1]));
    }

    private List<Integer> handleFrequencyOperation(String toParse) throws NumberFormatException
    {
        List<Integer> valuesToAdd = new ArrayList<>();

        String[] frequencies = toParse.split(CronFieldOperation.FREQUENCY.getRegex());
        for (String frequency : frequencies)
        {
            if (isNumeric(frequency))
            {
                valuesToAdd.addAll(cronoFieldType.getValuesByFrequency(Integer.parseInt(frequency)));
            }
            else if (!frequency.isEmpty())
            {
                throw new IllegalArgumentException("Invalid arguments for frequency values:" + frequency);
            }
        }
        return valuesToAdd;
    }

    private static boolean isNumeric(final CharSequence cs)
    {
        if (cs.length() == 0)
        {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (!Character.isDigit(cs.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}
