package ftcbootstrap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.lang.annotation.Annotation;

//import com.qualcomm.robotcore.eventloop.opmode.OpModeMeta;

/**
 * Register Op Modes
 */
public abstract class BootstrapRegistrar implements OpModeRegister {


  /**
   * register the opmodes with the opmode manager
   *
   * @param manager op mode manager
   */
  public void register(OpModeManager manager ) {

    for ( Class clazz : getOpmodeClasses()  )  {
      OpModeMeta opModeMeta = opModeMetaOn(clazz);
      if (opModeMeta != null) {
        manager.register(opModeMetaOn(clazz) , clazz);
      }

    }

  }

   protected abstract Class[] getOpmodeClasses();

  private OpModeMeta opModeMetaOn(Class clazz) {

    OpModeMeta meta= null;
    Annotation annotation = null;
    OpModeMeta.Flavor flavor = null;
    String groupName  = null;
    String name  = null;

    if (clazz.isAnnotationPresent(Autonomous.class)) {
      annotation = clazz.getAnnotation(Autonomous.class);
      flavor = OpModeMeta.Flavor.AUTONOMOUS;
      groupName = ((Autonomous) annotation).group();
      name = ((Autonomous) annotation).name();
    }
    else if (clazz.isAnnotationPresent(TeleOp.class)) {
      annotation = clazz.getAnnotation(TeleOp.class);
      flavor = OpModeMeta.Flavor.TELEOP;
      groupName = ((TeleOp) annotation).group();
      name = ((TeleOp) annotation).name();
    }
    else{
      flavor = OpModeMeta.Flavor.TELEOP;
    }

    if (name == null || name.equals("")) {
      name = clazz.getSimpleName();
    }
    meta = new OpModeMeta( name, flavor , groupName);

    return meta;

  }

}
