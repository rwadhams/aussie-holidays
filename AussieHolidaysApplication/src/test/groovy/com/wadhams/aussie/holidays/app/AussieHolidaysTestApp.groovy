package com.wadhams.aussie.holidays.app

import com.wadhams.aussie.holidays.dto.StateDTO
import com.wadhams.aussie.holidays.report.AussieHolidaysReportService
import com.wadhams.aussie.holidays.service.AussieHolidaysXMLService
import java.time.LocalDate
import java.time.Year

class AussieHolidaysTestApp {
	static main(args) {
		println 'AussieHolidaysTestApp started...'
		println ''

		AussieHolidaysXMLService xmlService = new AussieHolidaysXMLService()
		Year year = xmlService.getYear('AussieHolidaysTest.xml')
		println "year...: $year"
		println ''
		
		List<StateDTO> stateDTOList = xmlService.buildStateDTOList('AussieHolidaysTest.xml', year)
//		stateDTOList.each {s ->
//			println s
//		}

		AussieHolidaysReportService reportService = new AussieHolidaysReportService()
		
		//txt files
		File f1 = new File("out/${year}-school-holiday-report.txt")
		f1.withPrintWriter {pw ->
			reportService.reportTermHolidays(stateDTOList, year, pw)
		}
		
		File f2 = new File("out/${year}-holidays-by-state-report.txt")
		f2.withPrintWriter {pw ->
			reportService.reportHolidaysByState(stateDTOList, year, pw)
		}
		
		File f3 = new File("out/${year}-holidays-by-date-report.txt")
		f3.withPrintWriter {pw ->
			reportService.reportHolidaysByDate(stateDTOList, year, pw)
		}
		
		//html files
		File f1a = new File("out/${year}-school-holiday-report.html")
		f1a.withPrintWriter {pw ->
			reportService.reportTermHolidaysHtml(stateDTOList, year, pw)
		}
		
		File f2a = new File("out/${year}-holidays-by-state-report.html")
		f2a.withPrintWriter {pw ->
			reportService.reportHolidaysByStateHtml(stateDTOList, year, pw)
		}
		
		File f3a = new File("out/${year}-holidays-by-date-report.html")
		f3a.withPrintWriter {pw ->
			reportService.reportHolidaysByDateHtml(stateDTOList, year, pw)
		}

		println 'AussieHolidaysTestApp ended.'
	}
}
