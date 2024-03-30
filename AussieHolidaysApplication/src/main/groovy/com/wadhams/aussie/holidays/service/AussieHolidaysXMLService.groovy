package com.wadhams.aussie.holidays.service

import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import com.wadhams.aussie.holidays.dto.HolidayDTO
import com.wadhams.aussie.holidays.dto.StateDTO
import com.wadhams.aussie.holidays.dto.TermDTO

class AussieHolidaysXMLService {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern('dd/MM/yyyy')
	
	Year getYear(String filename) {
		def aussieHolidays = slurpXMLFile(filename)
		
		DateTimeFormatter dtfYear = DateTimeFormatter.ofPattern('yyyy')
		Year year = Year.parse(aussieHolidays.year.text(), dtfYear)
		//println startDate
		return year
	}
	
	List<StateDTO> buildStateDTOList(String filename, Year year) {
		List<StateDTO> sList = []

		def aussieHolidays = slurpXMLFile(filename)
		def states = aussieHolidays.state
		
		//extract values from XML
		states.each {s ->
			String stateName = s.name
			
			//terms
			def terms = s.term
			List<TermDTO> tList = buildTermDTOList(terms)
			
			//holidays
			def holidays = s.holiday
			List<HolidayDTO> hList = []
			holidays.each {h ->
				hList << new HolidayDTO(name : h.@name, date : LocalDate.parse(h.@date.text(), dtf))
			}
			
			sList << new StateDTO(name : stateName, terms : tList, holidays : hList)
		}
		
		//create termHolidays lists
		sList.each {s ->
			TermDTO t1 = s.terms.find {t -> t.num == 'One'}
			TermDTO t2 = s.terms.find {t -> t.num == 'Two'}
			TermDTO t3 = s.terms.find {t -> t.num == 'Three'}
			TermDTO t4 = s.terms.find {t -> t.num == 'Four'}
			
			s.termHolidays = []
			List<LocalDate> ldList
			LocalDate start
			LocalDate end
			
			//beforeSchoolYear
			ldList = []
			start = LocalDate.of(year.getValue(), 1, 1)
			end = t1.startDate - 1
			while (start.isBefore(end)) {
				ldList << start
				start = start + 1
			}
			ldList << end
			s.termHolidays << ldList
			
			//autumn
			ldList = []
			start = t1.endDate + 1
			end = t2.startDate - 1
			while (start.isBefore(end)) {
				ldList << start
				start = start + 1
			}
			ldList << end
			s.termHolidays << ldList
			
			//winter
			ldList = []
			start = t2.endDate + 1
			end = t3.startDate - 1
			while (start.isBefore(end)) {
				ldList << start
				start = start + 1
			}
			ldList << end
			s.termHolidays << ldList
			
			//spring
			ldList = []
			start = t3.endDate + 1
			end = t4.startDate - 1
			while (start.isBefore(end)) {
				ldList << start
				start = start + 1
			}
			ldList << end
			s.termHolidays << ldList
			
			//afterSchoolYear
			ldList = []
			start = t4.endDate + 1
			end = LocalDate.of(year.getValue(), 12, 31)
			while (start.isBefore(end)) {
				ldList << start
				start = start + 1
			}
			ldList << end
			s.termHolidays << ldList
		}
		
		return sList
	}
	
	List<TermDTO> buildTermDTOList(terms) {
		List<TermDTO> tList = []
		
		terms.each {t ->
			String termNum = t.num
			LocalDate startDate = LocalDate.parse(t.startDate.text(), dtf)
			LocalDate endDate = LocalDate.parse(t.endDate.text(), dtf)
			tList << new TermDTO(num : termNum, startDate : startDate, endDate : endDate)
		}
		
		return tList
	}

	def slurpXMLFile(String filename) {
		File aussieHolidaysFile
		URL resource = getClass().getClassLoader().getResource(filename)
		if (resource == null) {
			throw new IllegalArgumentException("file not found!")
		}
		else {
			aussieHolidaysFile = new File(resource.toURI())
		}
		
		return new XmlSlurper().parse(aussieHolidaysFile)
	}
	
}
