package GitApiTest;

import com.fasterxml.jackson.databind.JsonNode;
import models.ResponseParameters;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;

/**
 * Created by VBashynskyi on 15.09.2017.
 */
public class SampleTests {
    RestTemplate restTemplate = new RestTemplate();
    private final String ApiBase="https://api.github.com";

    @Test
    public void testUserInfoResponse(){
        String url = "https://github.com/vadim-bashynskiy";
       ResponseParameters parameters = restTemplate.getForObject(ApiBase+"/users/vadim-bashynskiy", ResponseParameters.class);
        assertTrue(parameters.getHtmlUrl().equals(url));
    }

    @Test
    public void testFollowers(){
        Integer myFollowersCount = 2;
        ResponseParameters[] followers = restTemplate.getForObject(ApiBase+ "/users/vadim-bashynskiy/followers", ResponseParameters[].class);
        System.out.println();
        assertTrue(followers.length == myFollowersCount);
    }

    @Test
    public void findCommit(){
        String shaCommit = "b16b0bb5cb7e43d68866cb5f4edb6fb09d788c76";
        JsonNode node = restTemplate.getForObject(ApiBase + "/repos/vadim-bashynskiy/javacore/commits/b16b0bb5cb7e43d68866cb5f4edb6fb09d788c76", JsonNode.class);
        assertTrue(node.get("sha").toString().replaceAll("\"","").equals(shaCommit));
    }
    @Test
    public void findListCommits(){
        ArrayList<String> sha = new ArrayList<>();
        ResponseParameters[] shaCommits = restTemplate.getForObject(ApiBase + "/repos/vadim-bashynskiy/javacore/commits", ResponseParameters[].class);
        for (ResponseParameters parameters:shaCommits) {
            sha.add(parameters.getSha());
        }
        assertTrue(sha.size()>= 30);

    }
}
