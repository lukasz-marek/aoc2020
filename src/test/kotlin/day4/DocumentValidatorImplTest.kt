package day4

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.hasSize

class DocumentValidatorImplTest {
    private val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"/*, "cid"*/)
    private val tested = DocumentValidatorImpl(requiredFields)
    private val input = """ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in""".split("\n").toList()
    private val inputDocuments = identifyDocuments(input).map { parseDocument(it) }

    @Test
    fun `should successfully identify documents as valid`() {
        // given / when
        val validDocuments = inputDocuments.filter { tested.isValid(it) }
        expectThat(validDocuments){
            hasSize(2)
            contains(inputDocuments[0], inputDocuments[2])
        }
    }

}