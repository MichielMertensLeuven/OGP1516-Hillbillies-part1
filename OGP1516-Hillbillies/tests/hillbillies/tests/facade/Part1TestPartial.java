package hillbillies.tests.facade;
// @author Michiel Mertens
import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static hillbillies.tests.util.PositionAsserts.assertIntegerPositionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.part1.facade.Facade;
import ogp.framework.util.ModelException;

public class Part1TestPartial {

	private Facade facade;

	@Before
	public void setup() {
		this.facade = new Facade();
	}
	
	
	
	@Test
	public void testCubeCoordinate() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
		assertIntegerPositionEquals("A valid position should be accepted", 1, 2, 3, facade.getCubeCoordinate(unit));
		System.out.println(Arrays.deepToString(new int[4][3][5]));
	}

	@Test
	public void testPosition() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 12, 11, 5 }, 50, 50, 50, 50, false);
		assertDoublePositionEquals("Position must be the center of the cube", 12.5, 11.5, 5.5,
				facade.getPosition(unit));
	}
	
	@Test
	public void testIllegalUnit() throws ModelException {
		try {
			facade.createUnit(null, new int[] { 12, 11, 5 }, 50, 50, 50, 50, false);
		} catch (ModelException e) {
			// that's OK
		}
	}	

	@Test
	public void testInitialAgilityTooLow() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 2, 3 }, 50, 24, 50, 50, false);
		assertTrue("An attribute value of 24 should be replaced with a valid value",
				25 <= facade.getAgility(unit) && facade.getAgility(unit) <= 100);
	}

	@Test
	public void testInitialAgilityTooHigh() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 2, 3 }, 50, 101, 50, 50, false);
		assertTrue("An attribute value of 101 should be replaced with a valid value",
				25 <= facade.getAgility(unit) && facade.getAgility(unit) <= 100);
	}
	
	@Test
	public void testSetValidName() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 1, 1 }, 50, 50, 50, 50, false);
		String validNames[] = new String[] { "John \"Johnnie\" O'Hare the first", "Jip", "Janneke", "Lesley Ann Poppe", "Albert II", "Alberto Vermicelli", "Ik"};
		for (int i = 0; i < validNames.length; i++) {
			facade.setName(unit, validNames[i]);
			assertEquals("This should be a valid name", validNames[i], facade.getName(unit));
		}
	}

	@Test
	public void testSetNameWithoutCapital() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 1, 1 }, 50, 50, 50, 50, false);
		try {
			facade.setName(unit, "john O'Hare");
		} catch (ModelException e) {
			// that's OK
		}
		assertEquals("This name is invalid because it doesn't start with a capital", "TestUnit", facade.getName(unit));
	}

	@Test
	public void testSetNameWithTooShortName() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 1, 1 }, 50, 50, 50, 50, false);
		try {
			facade.setName(unit, "J");
		} catch (ModelException e) {
			// that's OK
		}
		assertEquals("This name is invalid because it is too short", "TestUnit", facade.getName(unit));
	}

	@Test
	public void testSetNameWithIllegalCharacters() throws ModelException {
		String invalidNames[] = new String[] { "Jip & Janneke", "Lesley-Ann Poppe", "Albert2", "Albert�oo Vermicelli"};
		for (int i = 0; i < invalidNames.length; i++) {
			Unit unit = facade.createUnit("TestUnit", new int[] { 1, 1, 1 }, 50, 50, 50, 50, false);
			try {
				facade.setName(unit, invalidNames[i]);
			} catch (ModelException e) {
				// that's OK
			}
			assertEquals("This name is invalid because it contains invalid characters", "TestUnit", facade.getName(unit));
		}
	}
}
