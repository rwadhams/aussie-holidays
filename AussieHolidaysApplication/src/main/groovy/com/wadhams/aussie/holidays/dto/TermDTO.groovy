package com.wadhams.aussie.holidays.dto

import java.time.LocalDate

import groovy.transform.ToString

@ToString(includeNames=true)
class TermDTO {
	String num
	LocalDate startDate
	LocalDate endDate
}
