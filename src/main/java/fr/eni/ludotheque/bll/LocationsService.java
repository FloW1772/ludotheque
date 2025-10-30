package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dto.LocationDto;

public interface LocationsService {
    Location creerLocation(LocationDto locationDto);
}
