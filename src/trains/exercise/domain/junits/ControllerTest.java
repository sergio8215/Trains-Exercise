package trains.exercise.domain.junits;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import trains.exercise.domain.classes.Town;
import trains.exercise.domain.controller.Controller;

@RunWith(value = Parameterized.class)
public class ControllerTest {
	
	public static Iterable<Object[]> getData(){
		List<Object[]> obj = new ArrayList<>();
		obj.add(new Object[] {});
		return obj;
	}
	
	private Town start;
	private Town end;
	
	/**
	 * @param start
	 * @param end
	 */
	public ControllerTest(Town start, Town end) {
		this.start = start;
		this.end = end;
	}

	@Test
	public void computeDistanceTest() {
		Controller c = new Controller();
		
	}

}
