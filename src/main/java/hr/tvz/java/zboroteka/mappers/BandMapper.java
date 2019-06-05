package hr.tvz.java.zboroteka.mappers;

import java.util.Date;

import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.forms.BandForm;
import hr.tvz.java.zboroteka.model.Band;

@Component
public class BandMapper {

	BandMapper() {
		// default
	}

	public Band mapBandFormToBand(BandForm bandForm) {
		Band band = new Band();

		band.setId(bandForm.getId());

		band.setCreationDate(new Date());
		band.setDescription(bandForm.getDescription());
		band.setGroupType(bandForm.getGroupType());
		band.setName(bandForm.getName());

		// TODO ZASAD HARDKODIRANO JER NIJE IMPLENETIRANA PRIJAVA
		Integer userId = 1;
		// = (Integer) session.getAttribute("userId");
		band.setCreatorId(userId);
		
		band.setMembers(bandForm.getMembers());
		band.setBandSongs(bandForm.getBandSongs());
		
		return band;
	}
}
