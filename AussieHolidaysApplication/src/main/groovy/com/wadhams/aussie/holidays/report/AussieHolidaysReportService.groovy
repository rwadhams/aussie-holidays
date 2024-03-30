package com.wadhams.aussie.holidays.report

import com.wadhams.aussie.holidays.dto.StateDTO
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter

class AussieHolidaysReportService {
	def reportHolidaysByState(List<StateDTO> stateDTOList, Year year, PrintWriter pw) {
		DateTimeFormatter dayddmmyyyy = DateTimeFormatter.ofPattern('EEE dd/MM/yyyy')
		pw.println "Holidays reported by state for $year"
		pw.println '-----------------------------------'
		stateDTOList.each {s ->
			pw.println s.name
			s.holidays.each {h ->
				pw.println "\t${h.date.format(dayddmmyyyy)}\t${h.name}"
			}
			pw.println ''
		}
		pw.println ''
	}
	
	def reportHolidaysByDate(List<StateDTO> stateDTOList, Year year, PrintWriter pw) {
		DateTimeFormatter dayddmmyyyy = DateTimeFormatter.ofPattern('EEE dd/MM/yyyy')
		pw.println "Holidays reported by date for $year"
		pw.println '----------------------------------'

		//build map keyed by LocalDate		
		Map<LocalDate, Map<String, List>> dateMap = [:] as TreeMap
		stateDTOList.each {s ->
			s.holidays.each {h ->
				Map<String, List> holidayMap = dateMap[h.date]
				if (!holidayMap) {
					holidayMap = [:] as TreeMap
					holidayMap[h.name] = [s.name]
					dateMap[h.date] = holidayMap
				}
				else {
					List<String> holidayStateDTOList = holidayMap[h.name]
					if (!holidayStateDTOList) {
						holidayMap[h.name] = [s.name]
					}
					else {
						holidayStateDTOList << s.name
					} 
				}
			}
		}

		dateMap.each {k1,holidayMap ->
			pw.println "${k1.format(dayddmmyyyy)}"
			//find the length of the longest holiday name
			int holidayNameLength = 0
			holidayMap.keySet().each {hn ->
				if (hn.size() > holidayNameLength) {
					holidayNameLength = hn.size()
				}
			}
			
			holidayMap.each {k2,holidayStateDTOList ->
				pw.println "\t${k2.padRight(holidayNameLength, ' ')}   $holidayStateDTOList"
			}
			pw.println ''
		}
		pw.println ''
	}
	
	def reportTermHolidays(List<StateDTO> stateDTOList, Year year, PrintWriter pw) {
		DateTimeFormatter ddmm = DateTimeFormatter.ofPattern('dd/MM')

		pw.println "School Holidays for $year"
		pw.println '-------------------------'
		pw.println ''
		
		List<String> holidayNameList = ['BeforeSchoolYear','Autumn School Holidays','Winter School Holidays','Spring School Holidays','AfterSchoolYear']
		holidayNameList.size().times {index ->
			pw.println holidayNameList[index]
			Set<LocalDate> termHolidays = []
			stateDTOList.each {s ->
				termHolidays.addAll(s.termHolidays[index])
			}
			List<LocalDate> termHolidaysSorted = termHolidays.sort()
	
			termHolidaysSorted.each {ld ->
				pw.print "${ld.format(ddmm)} "
			}
			pw.println ''
			
			stateDTOList.each {s ->
				String stateName = s.name
				if (s.name.size() == 2) {
					stateName = "${s.name} "	//pad right with space
				}
				
				termHolidays.each {ld ->
					if (s.termHolidays[index].contains(ld)) {
						pw.print " ${stateName}  "
					}
					else {
						pw.print '      '
					}
				}
				pw.println ''
			}
			pw.println ''
		}
		pw.println ''
	}
	
	def reportHolidaysByStateHtml(List<StateDTO> stateDTOList, Year year, PrintWriter pw) {
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern('EEE')
		DateTimeFormatter ddmmyyyyFormatter = DateTimeFormatter.ofPattern('dd/MM/yyyy')
		pw.println '<html>'
		pw.println "<b>Holidays reported by state for $year</b>"
		pw.println '</br>'
		stateDTOList.each {s ->
			pw.println s.name
			pw.println '<table border="1">'
			s.holidays.each {h ->
				pw.println '<tr>'
				pw.println "<td>${h.date.format(dayFormatter)}</td><td>${h.date.format(ddmmyyyyFormatter)}</td><td>${h.name}</td>"
				pw.println '</tr>'
			}
			pw.println '</table>'
		}
		pw.println '</html>'
	}
	
	def reportHolidaysByDateHtml(List<StateDTO> stateDTOList, Year year, PrintWriter pw) {
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern('EEE')
		DateTimeFormatter ddmmyyyyFormatter = DateTimeFormatter.ofPattern('dd/MM/yyyy')
		pw.println '<html>'
		pw.println "<b>Holidays reported by date for $year</b>"
		pw.println '</br>'

		//build map keyed by LocalDate		
		Map<LocalDate, Map<String, List>> dateMap = [:] as TreeMap
		stateDTOList.each {s ->
			s.holidays.each {h ->
				Map<String, List> holidayMap = dateMap[h.date]
				if (!holidayMap) {
					holidayMap = [:] as TreeMap
					holidayMap[h.name] = [s.name]
					dateMap[h.date] = holidayMap
				}
				else {
					List<String> holidayStateDTOList = holidayMap[h.name]
					if (!holidayStateDTOList) {
						holidayMap[h.name] = [s.name]
					}
					else {
						holidayStateDTOList << s.name
					} 
				}
			}
		}

		pw.println '<table border="1">'
		dateMap.each {k1,holidayMap ->
			holidayMap.each {k2,holidayStateDTOList ->
				pw.println "<tr><td>${k1.format(dayFormatter)}</td><td>${k1.format(ddmmyyyyFormatter)}</td><td>${k2}</td><td>$holidayStateDTOList</td></tr>"
			}
		}
		pw.println '</table>'
		pw.println '</html>'
	}
	
	def reportTermHolidaysHtml(List<StateDTO> stateDTOList, Year year, PrintWriter pw) {
		DateTimeFormatter ddmm = DateTimeFormatter.ofPattern('dd/MM')

		pw.println '<html>'
		pw.println "<b>School Holidays for $year</b>"
		pw.println '</br>'
		
		List<String> holidayNameList = ['BeforeSchoolYear','Autumn School Holidays','Winter School Holidays','Spring School Holidays','AfterSchoolYear']
		holidayNameList.size().times {index ->
			pw.println holidayNameList[index]
			pw.println '<table border="1">'
			Set<LocalDate> termHolidays = []
			stateDTOList.each {s ->
				termHolidays.addAll(s.termHolidays[index])
			}
			List<LocalDate> termHolidaysSorted = termHolidays.sort()
	
			pw.println '<tr align="center">'
			termHolidaysSorted.each {ld ->
				pw.print "<td>${ld.format(ddmm)}</td>"
			}
			pw.println '</tr>'
			
			stateDTOList.each {s ->
				String stateName = s.name
				
				pw.println '<tr align="center">'
				termHolidays.each {ld ->
					if (s.termHolidays[index].contains(ld)) {
						pw.print "<td>${stateName}</td>"
					}
					else {
						pw.print '<td></td>'
					}
				}
				pw.println '</tr>'
			}
			pw.println '</table>'
		}
		pw.println '</html>'
	}
}
