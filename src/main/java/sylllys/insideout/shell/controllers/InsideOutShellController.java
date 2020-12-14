package sylllys.insideout.shell.controllers;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sylllys.insideout.shell.entities.pojo.SayHello;
import sylllys.insideout.shell.entities.pojo.ShellCommand;
import sylllys.insideout.shell.entities.pojo.ShellScript;
import sylllys.insideout.shell.factories.ShellFactory;

@RestController
@RequestMapping("/insideout/shell")
public class InsideOutShellController {

  private static final Logger logger = LogManager.getLogger(InsideOutShellController.class);

  @Autowired
  ShellFactory shellFactory;

  @GetMapping("/sayhello")
  public SayHello sayhello() {

    logger.info("Received request to say hello");
    return new SayHello();

  }

  @PostMapping("/command")
  public List<ShellCommand> shellCommand(@RequestBody List<ShellCommand> shellCommands) {

    logger.info("Received request to execute shell command");

    for (ShellCommand command : shellCommands) {

      shellFactory.executeCommand(command);

      if (command.getExitCode() != 0) {
        break;
      }
    }

    logger.info("Completed request to execute shell command");
    return shellCommands;
  }

  @PostMapping("/script")
  public List<ShellScript> shell(@RequestBody List<ShellScript> shellScripts) {

    logger.info("Received request to execute shell script");

    for (ShellScript shellScript : shellScripts) {

      shellFactory.executeShellScript(shellScript);

      if (shellScript.getExitCode() == null || shellScript.getExitCode() != 0) {
        break;
      }
    }

    logger.info("Completed request to execute shell script");
    return shellScripts;
  }
}
