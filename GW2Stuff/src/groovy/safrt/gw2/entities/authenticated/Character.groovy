package groovy.safrt.gw2.entities.authenticated
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import java.text.DateFormat
import static java.util.Calendar.*
import groovy.time.DatumDependentDuration
import groovy.time.TimeCategory
import groovy.lang.GroovyShell
import groovy.safrt.gw2.entities.local.LocalBag
import groovy.safrt.gw2.entities.local.LocalEquipment
import groovy.safrt.gw2.json.AnonJson
import groovy.safrt.gw2.json.AuthenticatedJSON
import groovy.safrt.gw2.utility.Birthday
import groovy.transform.Sortable

@Sortable(includes = ['name', 'age'])
class Character {
	String accountToken, name, race, profession, gender,  level, guild, created, age, deaths, crafting
	List<LocalEquipment> equipmentList = []
	List<LocalBag> bagList=[]
	
	Character(fullChar, accountToken) {

		age = fullChar.age
		def bagArray = fullChar.bags
		created = fullChar.created.replace("T", " ")
		deaths = fullChar.deaths
		crafting = fullChar.crafting
		def equipmentArray = fullChar.equipment

		name = fullChar.name
		race = fullChar.race
		gender = fullChar.gender
		profession = fullChar.profession
		level = fullChar.level
		guild = fullChar.guild
		this.accountToken = accountToken
		loadEquipment(equipmentArray)

		koadBags(bagArray)
	}
	

	
	def loadEquipment(equipmentArray){
		
		List localEquipment = []
		
		for(piece in equipmentArray){

			LocalEquipment eq = new LocalEquipment(piece.collect())
			eq.charId = name;
			
			equipmentList.add(eq)

		}
	}
	
	
	def koadBags(bagArray){
		for(bag in bagArray){

			Bag bg = new Bag(bag.collect())
			bagList.add(bg)
		}
	}
	
	Integer yearsOld (){
		Birthday.getYearsOld(daysOld().value).ordinal().toInteger()
	}
	
	Integer daysOld () {
		TimeCategory.minus( new Date() , Date.parse("yyyy-MM-dd hh:mm:ss", created) )
		.getDays()
		.toInteger()
	}
	
	
	Integer daysToNextBirthday(){
		Birthday.getDaysToNextBirthday(daysOld().value)}
	
	Integer daysSinceLastBirthday(){
		daysOld() - Birthday.getYearsOld(daysOld()).value
	}
	
	
	String getGenderPossessive(){
		(gender.equals("Male"))?"his":"her"
	}
	
	
	
}
