package com.diana.io.deliveroo;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>
 * </p>
 *
 * @author Diana Araujo
 */
@SuppressWarnings("nls")
public enum CronFieldOperation
{
    /**
     * All values included in a chrono field type.
     */
    ALL("*", "*"),
    /**
     * Specific values withing the chrono value
     */
    LIST(",", ","),
    /**
     * Range values within related to the {@link CronFieldType}
     */
    RANGE("-", "-"),
    /**
     * A frequency within the values related to the {@link CronFieldType}
     */
    FREQUENCY("*/", "\\*\\/");

    private final String symbol;
    private final String regex;

    private CronFieldOperation(String symbol, String regex)
    {
        this.symbol = symbol;
        this.regex = regex;
    }

    /**
     * @return
     */
    public String getSymbol()
    {
        return symbol;
    }

    /**
     * @return
     */
    public String getRegex()
    {
        return regex;
    }

    /**
     * @param symbol
     * @return
     */
    public static CronFieldOperation bySymbol(String symbol)
    {
        Optional<CronFieldOperation> isAChronoField = Stream.of(CronFieldOperation.values())
                .filter(status -> status.getSymbol().equals(symbol)).findFirst();
        if (isAChronoField.isPresent())
        {
            return isAChronoField.get();
        }
        return null;
    }
    
    public  boolean contains(String expression)
    {
        return expression.contains(symbol);
    } 
}
