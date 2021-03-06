package kn.uni.sen.joblibrary.tartar.job_experiment;

import static org.junit.Assert.assertTrue;

import kn.uni.sen.joblibrary.tartar.common.SMT2_OPTION;
import kn.uni.sen.joblibrary_tartar.job_experiment.Job_SeedExperiment;
import kn.uni.sen.jobscheduler.common.JobAbstractTest;
import kn.uni.sen.jobscheduler.common.JobDataTest;
import kn.uni.sen.jobscheduler.common.model.Job;
import kn.uni.sen.jobscheduler.common.model.ResourceInterface;
import kn.uni.sen.jobscheduler.common.resource.ResourceEnum;
import kn.uni.sen.jobscheduler.common.resource.ResourceFile;

public class Test_Job_SeedExperiment extends JobAbstractTest
{
	String modelFile = "test_good.xml";
	{
		// modelFile = "1repairedDB2.xml";
		// modelFile = "7SBR.xml";
		// modelFile = "6Pacemaker.xml";
		// modelFile = "8FDDI.xml";
		// modelFile = "1repairedDB3.xml";
	}

	String modelFileRef = "1repairedDB2.xml";
	String modelFileUrg = "1repairedDB2.xml";
	String modelFileRes = "1repairedDB2.xml";
	String modelFileOp = "1repairedDB2.xml";

	@Override
	protected Job createJob()
	{
		return new Job_SeedExperiment(this);
	}

	@Override
	protected JobDataTest createTest(Job jobTest2, int index)
	{
		SMT2_OPTION option = SMT2_OPTION.BOUNDARY;
		String file = modelFile;
		if (index == 1)
			; //return null;
		else if (index == 2)
		{
			option = SMT2_OPTION.URGENT;
			// file = modelFileUrg;
		} else if (index == 3)
		{
			option = SMT2_OPTION.RESET;
			// file = modelFileRes;
		} else if (index == 4)
		{
			option = SMT2_OPTION.REFERENCE;
			// file = modelFileRef;
		} else if (index == 5)
		{
			option = SMT2_OPTION.COMPARISON;
			// file = modelFileOp;
		} else
			return null;

		JobDataTest test = new JobDataTest();

		ResourceFile resFile = loadFile(file);
		assertTrue(resFile != null);
		test.add("Model", resFile);

		ResourceEnum resOpt = new ResourceEnum(option.toString());
		// ResourceEnum resOpt2 = new
		// ResourceEnum(SMT2_OPTION.RESET.toString());
		test.add("SeedKind", resOpt);

		ResourceEnum repOpt = new ResourceEnum(option.toString());
		ResourceEnum repOpt2 = new ResourceEnum(SMT2_OPTION.COMPARISON);
		repOpt.setNext(repOpt2);
		test.add("RepairKind", repOpt);

		return test;
	}

	// list.add("casestudy/1repairedDB.xml");
	// list.add("casestudy/2csma.xml");
	// list.add("casestudy/3elevator.xml");
	// list.add("casestudy/4viking.xml");
	// list.add("casestudy/5bando.xml");
	// list.add("casestudy/6Pacemaker.xml");
	// list.add("casestudy/7SBR.xml");
	// list.add("casestudy/8FDDI.xml");
	// System.out.println("End Experiment");

	public static void main(String args[])
	{
		// (new Test_Job_SeedExperiment()).testAll();
		(new Test_Job_SeedExperiment()).testSingle(1);
	}

	@Override
	protected ResourceInterface getResourceByName(String name, boolean out)
	{
		return null;
	}
}
