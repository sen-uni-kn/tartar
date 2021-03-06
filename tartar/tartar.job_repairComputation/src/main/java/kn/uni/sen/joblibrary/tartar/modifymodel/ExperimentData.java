package kn.uni.sen.joblibrary.tartar.modifymodel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import kn.uni.sen.joblibrary.tartar.common.SMT2_OPTION;
import kn.uni.sen.joblibrary.tartar.job_repaircomputation.Repair;
import kn.uni.sen.jobscheduler.common.resource.ResourceFile;

public class ExperimentData
{
	int seedCount = 0;
	Fault curFault = null;
	SMT2_OPTION seed;
	SMT2_OPTION rep;
	List<Fault> faultList = new ArrayList<>();

	public ExperimentData(SMT2_OPTION seed, SMT2_OPTION rep)
	{
		this.seed = seed;
		this.rep = rep;
	}

	/**
	 * Current fault seeded
	 * 
	 * @param fault
	 */
	public void addSeedFault(String fault)
	{
		seedCount++;
	}

	/**
	 * Seeded fault that also creates counterexample
	 * 
	 * @param fault
	 * @return
	 */
	public Fault addFault(String fault, long timeMS)
	{
		if (fault == null)
			return null;
		Fault fault2 = new Fault(fault, timeMS);
		faultList.add(fault2);
		return fault2;
	}

	public Fault addFault(String fault)
	{
		if (fault == null)
			return null;
		Fault fault2 = new Fault(fault, 0);
		faultList.add(fault2);
		return fault2;
	}

	/**
	 * Found repair
	 * 
	 * @param fault
	 * 
	 *            void addRepair(String repair, boolean admissible) { if
	 *            (curFault == null) return; Repair repair2 = new Repair(repair,
	 *            admissible); curFault.addRepair(repair2); repairCount++; if
	 *            (admissible) admissibleCount++; else inadmissibleCount++; }
	 */

	public int getSeedCount()
	{
		return seedCount;
	}

	public int getFaultCount()
	{
		return faultList.size();
	}

	private int getAdmRepairCount()
	{
		int repairCount = 0;
		for (Fault fault : faultList)
			if (fault.hasAdmissibleRepair())
				repairCount++;
		return repairCount;
	}

	private int getQETimeoutCount()
	{
		int timeoutCount = 0;
		for (Fault fault : faultList)
			if (fault.isQETimeout())
				timeoutCount++;
		return timeoutCount;
	}

	public int getRepairCount()
	{
		int repairCount = 0;
		for (Fault fault : faultList)
			repairCount += fault.getRepairCount();
		return repairCount;
	}

	public int getAdmissibleCount()
	{
		int count = 0;
		for (Fault fault : faultList)
			count += fault.getAdmissibleCount();
		return count;
	}

	public long getTimeQEMax()
	{
		long max = 0;
		for (Fault fault : faultList)
		{
			long time = fault.getTimeQE();
			if (time > max)
				max = time;
		}
		return max;
	}

	public long getTimeRepair()
	{
		long count = 0;
		for (Fault fault : faultList)
			count += fault.getTimeRepair();
		return count;
	}

	public long getMaxRepair()
	{
		long max = 0;
		for (Fault fault : faultList)
		{
			long time = fault.getTimeRepairMax();
			if (time > max)
				max = time;
		}
		return max;
	}

	public long getTimeAdmissible()
	{
		long count = 0;
		for (Fault fault : faultList)
			count += fault.getTimeAdm();
		return count;
	}

	public long getMaxAdm()
	{
		long max = 0;
		for (Fault fault : faultList)
		{
			long time = fault.getTimeAdmMax();
			if (time > max)
				max = time;
		}
		return max;
	}

	public double getMaxMemory()
	{
		double max = 0;
		for (Fault fault : faultList)
		{
			double mem = fault.getMaxMemory();
			if (mem > max)
				max = mem;
		}
		return max;
	}

	public double getMaxMemoryZ3()
	{
		double max = 0;
		for (Fault fault : faultList)
		{
			double mem = fault.getMaxMemoryZ3();
			if (mem > max)
				max = mem;
		}
		return max;
	}

	public int getVarZ3()
	{
		int max = 0;
		for (Fault fault : faultList)
		{
			int count = fault.getVarZ3();
			if (count > max)
				max = count;
		}
		return max;
	}

	public int getAssertZ3()
	{
		int max = 0;
		for (Fault fault : faultList)
		{
			int count = fault.getAssertZ3();
			if (count > max)
				max = count;
		}
		return max;
	}

	public int getMaxTraceLength()
	{
		int max = 0;
		for (Fault fault : faultList)
		{
			int l = fault.getMaxTraceLength();
			if (l > max)
				max = l;
		}
		return max;
	}

	public int getMaxConstraintMod()
	{
		int max = 0;
		for (Fault fault : faultList)
		{
			List<String> list = fault.getChangeList();
			if (list == null)
				continue;
			int m = list.size();
			if (m > max)
				max = m;
		}
		return max;
	}
	
	public int getMaxConstraintModAdm()
	{
		int max = 0;
		for (Fault fault : faultList)
		{
			List<String> list = fault.getChangeAdmList();
			if (list == null)
				continue;
			int m = list.size();
			if (m > max)
				max = m;
		}
		return max;
	}

	private long getMaxUppaalFaultTime()
	{
		long max = 0;
		for (Fault fault : faultList)
		{
			long l = fault.getUppaalTime();
			if (l > max)
				max = l;
		}
		return max;
	}

	private double getRepairTimeEstimation()
	{

		double var = 0;
		int count = 0;
		for (Fault fault : faultList)
		{
			var += fault.getTimeRepair();
			count += fault.getRepairCount();
		}
		if (count == 0)
			count = 1;
		return var / count;
	}

	// private double getRepairTimeDeviation(double er)
	// {
	// double var = 0;
	// int count = 0;
	// for (Fault fault : faultList)
	// {
	// for (Repair rep : fault.getRepairList())
	// {
	// double dif = rep.getTimeRepair() / 1000.0;
	// dif = dif - er;
	// dif = dif * dif;
	// var += dif;
	// count++;
	// }
	// }
	// if (count == 0)
	// return 0;
	// return Math.sqrt(var) / count;
	// }

	final static DecimalFormat f = new DecimalFormat("#0.000");

	public static String round3(double value)
	{
		double toFormat = ((double) Math.round(value * 1000)) / 1000.0;
		return f.format(toFormat);
	}

	static final String sep = " & ";

	public static void writeHead(String fileName)
	{
		StringBuilder builder = new StringBuilder();
		// head
		builder.append("" + sep);
		builder.append("Seed-Kind" + sep);
		builder.append("Repair-Kind" + sep);
		builder.append("Seed_Count(#Sd)" + sep);
		builder.append("TDT(#T)" + sep);
		builder.append("Time Uppaal(T_UP)" + sep);
		builder.append("Max Length(Ln)" + sep);
		builder.append("Repairs(#R)" + sep);
		builder.append("Admissible(#A)" + sep);
		builder.append("Solve-T(#S_model)" + sep);
		builder.append("Solve-R(#S_repair)" + sep);
		builder.append("Time QE(T_QE)" + sep);
		builder.append("Timouts(#O)" + sep);
		builder.append("Time Repair(T_R)" + sep);
		// builder.append("Deviation" + sep);
		builder.append("Max mem Z3(M_R)" + sep);

		builder.append("Max Var(#Vr)" + sep);
		builder.append("Max Assert(#Cn)" + sep);
		builder.append("Max Adm Time(T_Adm)" + sep);
		builder.append("Max mem Java(M_A)" + sep);

		builder.append("Max Sol" + sep);
		builder.append("Max mod.Con" + sep);
		builder.append("Max mod.AdmCon");
		builder.append("\n");
		ResourceFile.appendText2File(fileName, builder.toString());
	}

	public void writeData(String fileName, String name, int tSol, int sols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(name + sep);
		builder.append(SMT2_OPTION.getLongName(seed) + sep);
		builder.append(SMT2_OPTION.getLongName(rep) + sep);
		builder.append(seedCount + sep);
		int faultCount = getFaultCount();
		builder.append(faultCount + sep);
		double uppaal = getMaxUppaalFaultTime() / 1000.0;
		builder.append(round3(uppaal) + "s" + sep);
		builder.append(getMaxTraceLength() + sep);
		int repCount = getRepairCount();
		builder.append(repCount + sep);
		int admCount = getAdmissibleCount();
		builder.append(admCount + sep);
		builder.append(tSol + sep);
		builder.append(getAdmRepairCount() + sep);

		builder.append(round3(getTimeQEMax() / 1000.0) + "s" + sep);
		builder.append(getQETimeoutCount() + sep);

		// add faultCount to repCount, since last z3 call is always unsat
		double time = getRepairTimeEstimation() / 1000.0;
		builder.append(round3(time) + "s" + sep);
		// time = getRepairTimeDeviation(time);
		// builder.append(round3(time) + sep);
		// time = getMaxRepair() / 1000.0;
		// builder.append(round3(time) + "s" + sep);
		// time = getTimeAdmissible() / 1000.0 / repCount;
		builder.append(round3(getMaxMemoryZ3()) + "MB" + sep);

		// builder.append(round3(time) + "s" + sep);
		builder.append(getVarZ3() + sep);
		builder.append(getAssertZ3() + sep);
		time = getMaxAdm() / 1000.0;
		builder.append(round3(time) + "s" + sep);
		builder.append(round3(getMaxMemory()) + "MB" + sep);
		builder.append(sols + sep);
		builder.append(getMaxConstraintMod()+sep);
		builder.append(getMaxConstraintModAdm());
		
		String text = builder.toString().replace(",", ".");
		ResourceFile.appendText2File(fileName, text);
	}

	public void writeAnalyse(String fileName)
	{
		String sep = ",";
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (Fault fault : this.faultList)
		{
			if (!!!first)
				builder.append("\n");
			first = false;
			builder.append(fault.getMaxTraceLength() + sep);
			builder.append(fault.getVarZ3() + sep);
			builder.append(fault.getAssertZ3() + sep);
			builder.append(fault.getTimeQE() + sep);
			Repair rep = fault.getRepairByIndex(0);
			if (rep != null)
			{
				builder.append(rep.getTimeRepair());
			}
		}
		String text = builder.toString();
		ResourceFile.appendText2File(fileName, text);
	}

	public void writeDataLine(String fileName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(SMT2_OPTION.getLongName(seed));
		builder.append(" " + SMT2_OPTION.getLongName(rep));
		builder.append("	Seedcount 	" + seedCount);
		int faultCount = getFaultCount();
		builder.append("	Faultcount 	" + faultCount);
		builder.append("	Solve-R 	" + getAdmRepairCount());
		int repCount = getRepairCount();
		builder.append("	Repairs 	" + repCount);
		int admCount = getAdmissibleCount();
		builder.append("	Admissible 	" + admCount);
		int z3Count = repCount + faultCount;
		if (z3Count == 0)
			z3Count = 1;
		builder.append("	Time QE 	" + round3(getTimeQEMax() / 1000) + "s");
		double time = getTimeRepair() / 1000.0 / z3Count;
		builder.append("	Time Repair 	" + round3(time) + "s");
		if (repCount == 0)
			repCount = 1;
		time = getTimeAdmissible() / 1000.0 / repCount;
		builder.append("	Time Adm 	" + round3(time) + "s");

		builder.append("	Max Length	" + getMaxTraceLength());
		builder.append("	Max Var 	" + getVarZ3());
		builder.append("	Max Assert 	" + getAssertZ3());
		builder.append("	Max mem Java	" + round3(getMaxMemory()) + "MB");
		builder.append("	Max mem Z3 	" + round3(getMaxMemoryZ3()) + "MB");
		String text = builder.toString().replace(",", ".");
		ResourceFile.appendText2File(fileName, text);
	}
}
