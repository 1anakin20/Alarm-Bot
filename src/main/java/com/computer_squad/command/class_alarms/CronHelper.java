package com.computer_squad.command.class_alarms;


import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.Weekdays;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;

/**
 * Helper for the Cron expressions
 */
public class CronHelper {
	/** Creates a weekly Quartz cron expressions
	 * @param weekDayName Day of the week: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
	 * @param hours Hour of the day in 24h format
	 * @param minutes Minutes of the day
	 * @return Cron expression
	 * @throws IllegalArgumentException The day of the week is not valid
	 */
	public static String CronQuartzExpressionCreator(String weekDayName, int hours, int minutes) throws IllegalArgumentException {
		try {
			Weekdays weekday = Weekdays.valueOf(weekDayName.toUpperCase());
			// Not sure if it's a bug on cronutils, the day is behind 1. Related issue: https://github.com/jmrozanec/cron-utils/issues/114
			// Maybe their calendar starts on monday?
			int day = weekday.getWeekday() + 1;
			Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
					.withYear(always())
					.withDoM(questionMark())
					.withMonth(always())
					.withDoW(on(day))
					.withHour(on(hours))
					.withMinute(on(minutes))
					.withSecond(on(0))
					.instance();
			return cron.asString();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Week day is not valid");
		}
	}
}
