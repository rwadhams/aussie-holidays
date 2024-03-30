package com.wadhams.aussie.holidays.dto

import java.time.LocalDate

import groovy.transform.ToString

@ToString(includeNames=true)
class StateDTO {
	String name
	List<TermDTO> terms
	List<List<LocalDate>> termHolidays
	List<HolidayDTO> holidays
}
