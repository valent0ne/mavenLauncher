## mavenLauncher

This is a small CLI .jar that allows you to run maven goals.

Usage: java -jar mavenLauncher.jar [options]
  
	  Options:
	    --goal, -g
	      maven goals (es. -g goal1 -g goal2)
	      Default: [clean, install]
	    --project, -p [required]
	      absolute path to project directory