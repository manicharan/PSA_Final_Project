package travellingsalesman;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAdd {

	@Test
	public void testAdd() {
		assertEquals(5,TSP.add(2,3));
	}

}
