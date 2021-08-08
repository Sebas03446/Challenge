import com.Properties.domain.entity.Location;
import com.Properties.domain.entity.Pricing;
import com.Properties.domain.entity.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertiesTest {
    @Test
    void givenProperty_WhenCreatePropertyIsCalled_thenSavedIsReturned(){
        Property propertyTest = new Property();
        Location locationTest = new Location();
        locationTest.setLatitude((float) 19.481482);
        locationTest.setLongitude((float) -99.106636);
        Pricing priceTest = new Pricing();
        priceTest.setSalePrice(5450000);
        propertyTest.setTitle("Test");
        propertyTest.setDescription("Test");
        propertyTest.setLocation(locationTest);
        propertyTest.setPricing(priceTest);
        propertyTest.setPropertyType("HOUSE");
        propertyTest.setBedrooms(5);
        propertyTest.setBathrooms(3);
        propertyTest.setParkingSpots(1);
        propertyTest.setArea(70);
        //PropertyController propertyController = new PropertyController();
        //assertEquals("Saved!",propertyController.);

    }
}
