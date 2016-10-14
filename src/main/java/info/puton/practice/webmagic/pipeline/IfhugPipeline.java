package info.puton.practice.webmagic.pipeline;

import info.puton.practice.webmagic.model.Website;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by taoyang on 2016/10/13.
 */
public class IfhugPipeline implements Pipeline {
    public void process(ResultItems resultItems, Task task) {
        Website website = resultItems.get("website");
        System.out.println(website);
    }
}
