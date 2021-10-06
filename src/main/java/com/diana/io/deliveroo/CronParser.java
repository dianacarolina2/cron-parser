package com.diana.io.deliveroo;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * A program to parse Cron expressions and produce a user-friendly output.
 * </p>
 *
 * @author Diana Araujo
 */
public class CronParser
{
    private static final String CRON_EXPRESSION_SEPARATOR = " ";

    /**
     * Parses the provided Cron expression.
     * 
     * @param cronExpression
     *            the Cron formatted expression with space-separated elements
     * 
     * @throws IllegalArgumentException
     *             if the expression contains an invalid number of parameters
     */
    public static void parse(String cronExpression)
    {
        List<String> elementsToParse = Arrays.asList(cronExpression.split(CRON_EXPRESSION_SEPARATOR));

        if (elementsToParse.size() < 6)
        {
            throw new IllegalArgumentException("Invalid number of parameters"); //$NON-NLS-1$
        }

        for (int i = 0; i < 5; i++)
        {
            CronFieldParser cronFieldParser = new CronFieldParser(CronFieldType.getFieldTypeByIndex(i),
                    elementsToParse.get(i));
            cronFieldParser.parse();
            cronFieldParser.print();
        }

        String formattedCommand = String.format("%" + -14 + "s", "command");
        System.out.print(formattedCommand);
        elementsToParse.subList(5, elementsToParse.size()).forEach(element -> {
            System.out.print(element + " ");
        });
        System.out.println();
    }
}
