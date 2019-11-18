import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import py4j.GatewayServer;

public class GRAVToolWindowFactory implements ToolWindowFactory {
    public static GRAV_Window graph_window;
    static{
        graph_window = new GRAV_Window();
        // (py4j) - start gateway server for python to java communication
        GatewayServer server = new GatewayServer(graph_window);
        server.start();
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(graph_window.getContent(), "Graph Visualization", false);
        toolWindow.getContentManager().addContent(content);

    }
}


