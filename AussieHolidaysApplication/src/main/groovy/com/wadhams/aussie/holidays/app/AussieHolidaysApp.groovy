package com.wadhams.aussie.holidays.app

import java.time.LocalDate
import java.time.Year
import com.wadhams.aussie.holidays.app.AussieHolidaysApp
import com.wadhams.aussie.holidays.dto.StateDTO
import com.wadhams.aussie.holidays.report.AussieHolidaysReportService
import com.wadhams.aussie.holidays.service.AussieHolidaysXMLService

class AussieHolidaysApp {
	static main(args) {
		println 'AussieHolidaysApp started...'
		println ''
		
		if (args.size() == 1) {
			String filename = "AussieHolidays${args[0]}.xml"
			println "\tFilename: $filename"
			println ''

			AussieHolidaysApp app = new AussieHolidaysApp()
			app.execute(filename)
		}
		else {
			println "Invalid number of arguments. args.size(): ${args.size()}"
			println ''
			println 'Usage: AussieHolidaysApp <file suffix year>'
			println '<file suffix year> = must be a year (nnnn = 2023, 2024...).'
			println 'Filename pattern will be AussieHolidays<nnnn>.xml'
			println ''
		}
		
		println 'AussieHolidaysApp ended.'
	}
	
	def execute(String filename) {
		AussieHolidaysXMLService xmlService = new AussieHolidaysXMLService()
		Year year = xmlService.getYear(filename)
		List<StateDTO> stateDTOList = xmlService.buildStateDTOList(filename, year)

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

		println '\tAussie Holiday reports can be found in the \'out\' folder.'
		println ''
	}
	
}
