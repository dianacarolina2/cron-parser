package com.diana.io.deliveroo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class CronParserTest
{

    @AfterEach
    private void after()
    {
        System.out.println("------------------------------------");
    }

    @Test
    public void testParseMainExample()
    {
        String expression = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronParser.parse(expression);
    }

    @Test
    public void testParseEdgeCases()
    {
        String expression = "*/15 0 1,5,7-9 * 1-5 /usr/bin/find";
        CronParser.parse(expression);
    }

    @Test
    public void testParseEdgeCases_DoubleFrequency()
    {
        String expression = "*/2*/9 0 1-9 * 2,3,4 /usr/bin/find";
        CronParser.parse(expression);
    }

    @Test
    public void testParseEdgeCases_InvalidFrequency()
    {
        String expression = "*2/ 0 1,5,7-9 * 2 /usr/bin/find";
        assertThrows(IllegalArgumentException.class, () -> {
            CronParser.parse(expression);
        });
    }

}
