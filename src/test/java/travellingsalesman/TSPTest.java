package travellingsalesman;

import org.junit.Assert;
import org.junit.Test;

public class TSPTest {

    @Test
    public void testComputeDistance() {
        Assert.assertEquals(8240, Graph.computeDistance(new Vertex("1", -0.009691, 51.483548,0.0,0.0), new Vertex("2", -0.118888, 51.513075,0.0,0.0)), 10);
    }
}
