package ch.idsia.utils;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.AgentsPool;
import ch.idsia.ai.agents.human.HumanKeyboardAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, firstName_at_idsia_dot_ch
 * Date: May 5, 2009
 * Time: 9:34:33 PM
 * Package: ch.idsia.utils
 */
public class ParameterContainer
{
    protected HashMap<String, String> optionsHashMap = new HashMap<String, String>();
    private static List<String> allowedOptions = null;
    protected static HashMap<String, String> defaultOptionsHashMap = null;
    private String[] allowed = null;

    public ParameterContainer()
    {
        if (allowed == null)
            allowed = new String[]{
                    "-ag",
                    "-amico",
                    "-echo",
                    "-ewf",
                    "-gv",
                    "-gvc",
                    "-i",
                    "-ld",
                    "-ll",
                    "-ls",
                    "-lt",
                    "-m",
                    "-mm",
                    "-fps",
                    "-pr",
                    "-pw",
                    "-pym",
                    "-t",
                    "-tc",
                    "-tl",
                    "-vaot",
                    "-vis",
                    "-vlx",
                    "-vly",
                    "-ze",
                    "-zs"
            };
        if (allowedOptions == null)
        {
            allowedOptions = new ArrayList<String>();
            Collections.addAll(allowedOptions, allowed);
        }

        InitDefaults();
    }

    public void setParameterValue(String param, String value)
    {
        try
        {
            if (allowedOptions.contains(param))
            {
                optionsHashMap.put(param, value);
            }
            else
            {
                throw new IllegalArgumentException("Parameter " + param + " is not valid. Typo?");
            }
        }
        catch (IllegalArgumentException e)
        {

            System.err.println("Error: Undefined parameter '" + param + " " + value + "'");
            System.err.println(e.getMessage());
            System.err.println("Some defaults might be used instead");
        }
    }

    public String getParameterValue(String param)
    {
        if (allowedOptions.contains(param))
        {
            if (optionsHashMap.get(param) == null)
            {
                //System.err.println("InfoWarning: Default value '" + defaultOptionsHashMap.get(param) + "' for " + param +
                //        " used");
                optionsHashMap.put(param, defaultOptionsHashMap.get(param));
            }
            return optionsHashMap.get(param);
        }
        else
        {
            System.err.println("Parameter " + param + " is not valid. Typo?");
            return "";
        }
    }

    public int i(String s)
    {
        return Integer.parseInt(s);
    }

    public String s(Object i)
    {
        return String.valueOf(i);
    }

    public String s(Agent a)
    {
        try
        {
            if (AgentsPool.getAgentByName(a.getName()) == null)
                AgentsPool.addAgent(a);
            return a.getName();
        }catch(NullPointerException e)
        {
            System.err.println("ERROR: Agent Not Found");
            return "";
        }
    }

    public Agent a(String s)
    {
        return AgentsPool.getAgentByName(s);
    }

    public boolean b(String s)
    {
        return "on".equals(s) || Boolean.valueOf(s);
    }

    public static void InitDefaults()
    {
        if (defaultOptionsHashMap != null)
            return;
        else
        {
            defaultOptionsHashMap = new HashMap<String, String>();
            AgentsPool.setCurrentAgent(new HumanKeyboardAgent());
            defaultOptionsHashMap.put("-ag","ch.idsia.ai.agents.human.HumanKeyboardAgent"); //defaultOptionsHashMap.put("-agentName","NoAgent");
            defaultOptionsHashMap.put("-amico","off");
            defaultOptionsHashMap.put("-echo","off"); //defaultOptionsHashMap.put("-echo","off");
            defaultOptionsHashMap.put("-ewf","on"); //defaultOptionsHashMap.put("-exitWhenFinished","off");
            defaultOptionsHashMap.put("-gv","off"); //defaultOptionsHashMap.put("-gameViewer","off");
            defaultOptionsHashMap.put("-gvc","off"); //defaultOptionsHashMap.put("-gameViewerContinuousUpdates","off");
            defaultOptionsHashMap.put("-i","off"); // Invulnerability
            defaultOptionsHashMap.put("-ld","0"); //defaultOptionsHashMap.put("-levelDifficulty","0");
            defaultOptionsHashMap.put("-ll","320"); //defaultOptionsHashMap.put("-levelLength","320");
            defaultOptionsHashMap.put("-ls","0"); //defaultOptionsHashMap.put("-levelRandSeed","1");
            defaultOptionsHashMap.put("-lt","0"); //defaultOptionsHashMap.put("-levelType","1");
            // TODO:SK turn out to just FPS, if FPS > 100 -> make it maximum
            defaultOptionsHashMap.put("-fps", "24"); //defaultOptionsHashMap.put("-maxFPS","off");
            defaultOptionsHashMap.put("-m",""); //defaultOptionsHashMap.put("-matLabFile","DefaultMatlabFile");
            defaultOptionsHashMap.put("-mm","2"); //Mario Mode
//            defaultOptionsHashMap.put("-not","1"); //defaultOptionsHashMap.put("-attemptsNumber","5");
            defaultOptionsHashMap.put("-pw","off"); //defaultOptionsHashMap.put("-PauseWorld","off");
//            defaultOptionsHashMap.put("-port","4242"); //defaultOptionsHashMap.put("-port","4242");
            defaultOptionsHashMap.put("-pr","off"); //defaultOptionsHashMap.put("-powerRestoration","off");
//            defaultOptionsHashMap.put("-ssiw","off"); //defaultOptionsHashMap.put("-stopSimulationIfWin","off");
//            defaultOptionsHashMap.put("-server","off");
            defaultOptionsHashMap.put("-t","on"); //defaultOptionsHashMap.put("-timer","on");
            defaultOptionsHashMap.put("-tl","200"); //Time Limit
            defaultOptionsHashMap.put("-tc","off"); //defaultOptionsHashMap.put("-toolsConfigurator","off");
            defaultOptionsHashMap.put("-vaot","off"); //defaultOptionsHashMap.put("-viewAlwaysOnTop","off");
            defaultOptionsHashMap.put("-vlx","0"); //defaultOptionsHashMap.put("-viewLocationX","0");
            defaultOptionsHashMap.put("-vly","0"); //defaultOptionsHashMap.put("-viewLocationY","0");
            defaultOptionsHashMap.put("-vis","on"); //defaultOptionsHashMap.put("-visual","on");
            defaultOptionsHashMap.put("-zs","1");  // ZoomLevel of LevelScene observation
            defaultOptionsHashMap.put("-ze","0"); //  ZoomLevel of Enemies observation
        }
    }

    public static String getDefaultParameterValue(String param)
    {
        if (allowedOptions.contains(param))
        {
            assert (defaultOptionsHashMap.get(param) != null);
            return defaultOptionsHashMap.get(param);
        }
        else
        {
            System.err.println("Requires for Default Parameter " + param + " Failed. Typo?");
            return "";
        }
    }
}