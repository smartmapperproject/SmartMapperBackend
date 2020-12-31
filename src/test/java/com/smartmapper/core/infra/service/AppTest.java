/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.smartmapper.core.infra.service;

import com.smartmapper.AppMain;
import com.smartmapper.core.domain.model.Categorie;
import com.smartmapper.core.domain.model.Coordonnees;
import com.smartmapper.core.domain.model.PointInteret;
import com.smartmapper.core.infra.repository.PointInteretRepository;
import com.smartmapper.core.infra.service.serviceImpl.PointInteretServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppTest {
    @Test
    public void testAppHasAGreeting() {
        AppMain classUnderTest = new AppMain();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }


    @Test
    public void should_get_distance_between_two_points() {
        // Given:


        PointInteretServiceImpl test = new PointInteretServiceImpl(null);
        //given
        double lat1 = 48.9504898;
        double lg1 = 2.1638332;

        double lat2 = 48.822469;
        double lg2 = 2.245169;
        double expected = 0.15167345711850837877966928702302;
        //when
        double actual = test.distance(lat1, lg1, lat2, lg2);
        //then
        assertEquals(expected, actual, 1e-9);
    }
}