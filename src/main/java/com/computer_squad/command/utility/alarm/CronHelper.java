package com.computer_squad.command.utility.alarm;


import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.Weekdays;

import static com.cronutils.model.field.expression.FieldExpressionFactory.always;
import static com.cronutils.model.field.expression.FieldExpressionFactory.on;

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
	public static String CronUnixExpressionCreator(String weekDayName, int hours, int minutes) throws IllegalArgumentException {
		try {
			Weekdays weekday = Weekdays.valueOf(weekDayName.toUpperCase());
			int day = weekday.getWeekday();
			Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX))
					.withMinute(on(minutes))
					.withHour(on(hours))
					.withDoM(always())
					.withMonth(always())
					.withDoW(on(day))
					.instance();
			return cron.asString();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Week day is not valid");
		}
	}
}
