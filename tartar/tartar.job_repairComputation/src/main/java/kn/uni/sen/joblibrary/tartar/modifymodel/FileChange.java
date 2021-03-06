package kn.uni.sen.joblibrary.tartar.modifymodel;

import kn.uni.sen.joblibrary.tartar.job_repaircomputation.Repair;

public class FileChange
{
	// name of file which was corrupted
	String file;
	// changed text in file
	Repair corruption;

	public FileChange(String file, Repair corruption)
	{
		this.file = file;
		this.corruption = corruption;
	}

	public String getFile()
	{
		return file;
	}

	public String getText()
	{
		if (corruption == null)
			return "";
		return corruption.getModificationText();
	}
}
