package com.cnsi.story.trace;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trace {
	public static Writer writer= null;
	// directory where the stack files are written
	private static final String STACK_DUMP_DIR = "C:\\logs1";
	private static List<String> className = new ArrayList<String>();
	private static List<StackTraceElement> finalList = new ArrayList<StackTraceElement>();

	// here for testing
	public static void main(String[] args) throws Exception {
		// dumpStacks();
	}
	
	public static List<String> classNames(String classz){
		className.add(classz);
		return className;
	}

	public static void dumpStacks() throws IOException {
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] threadInfos = mxBean.getThreadInfo(
				mxBean.getAllThreadIds(), 0);
		Map<Long, ThreadInfo> threadInfoMap = new HashMap<Long, ThreadInfo>();
		for (ThreadInfo threadInfo : threadInfos) {
			threadInfoMap.put(threadInfo.getThreadId(), threadInfo);
		}
		/*String str = contextPath.replaceAll("\\/", "");*/
		//System.out.println(str);
		// choose our dump-file
		
		
		try {
			dumpTraces(mxBean, threadInfoMap, writer, className);
		} finally {
			//className.clear();
		//	finalList.clear();
		
		}
	}

	private static void dumpTraces(ThreadMXBean mxBean,
			Map<Long, ThreadInfo> threadInfoMap, Writer writer, List<String> className)
			throws IOException {
		Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
/*		writer.write("Dump of "
				+ stacks.size()
				+ " thread at "
				+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z")
						.format(new Date(System.currentTimeMillis())) + "\n\n");*/
		
		for (Map.Entry<Thread, StackTraceElement[]> entry : stacks.entrySet()) {
			Thread thread = entry.getKey();

		/*	writer.write("\"" + thread.getName() + "\" prio="
					+ thread.getPriority() + " tid=" + thread.getId() + " "
					+ thread.getState() + " "
					+ (thread.isDaemon() ? "deamon" : "worker") + "\n");*/
			
			ThreadInfo threadInfo = threadInfoMap.get(thread.getId());
			if ((threadInfo != null)  ) {/*
				
				
				writer.write("    native=" + threadInfo.isInNative()
						+ ", suspended=" + threadInfo.isSuspended()
						+ ", block=" + threadInfo.getBlockedCount() + ", wait="
						+ threadInfo.getWaitedCount() + "\n");
				writer.write("    lock="
						+ threadInfo.getLockName()
						+ " owned by "
						+ threadInfo.getLockOwnerName()
						+ " ("
						+ threadInfo.getLockOwnerId()
						+ "), cpu="
						+ (mxBean.getThreadCpuTime(threadInfo.getThreadId()) / 1000000L)
						+ ", user="
						+ (mxBean.getThreadUserTime(threadInfo.getThreadId()) / 1000000L)
						+ "\n");
				

				
			//	writer.write("\nClass Name" );
			*/}
			
		/*	List<StackTraceElement> ele = new ArrayList<StackTraceElement>();
				for (StackTraceElement element : entry.getValue()) {
					ele.add(element);
			}
				compareList(ele,className);*/
			
			List<StackTraceElement> tempList = new ArrayList<StackTraceElement>();
			for (StackTraceElement element : entry.getValue()) {
	//			writer.write("        ");
				
				tempList.add(element);
					/*writer.write("Class>>" + element.getClassName());
					writer.write("\tMethod Called>>" + element.getMethodName());
					writer.write("\n");*/
			}
		
		compareList(tempList, className);
		
		
		}

	}
	


	private static void  compareList(List<StackTraceElement> ele, List<String> classN) throws IOException{
		
		for (StackTraceElement el : ele){
			
			for (int i =0; i <classN.size(); i ++){
				if (el.getClassName().toString().contains(classN.get(i).toString())){
					finalList.add(el);
				}
			}
		}
		
		//return finalList;
		
	}
	
	
	public void dumpTrace() throws IOException{
		try{
		String fileName = className.get(0);
		String [] arr = fileName.split("\\.");
		String finalFileName = arr[4];
		String workingDir = System.getProperty("user.id");
		File dir = new File("c://logs1");
		if (!dir.exists()){
			dir.mkdir();
		}
		
		File dumpFile = new File(dir.getAbsolutePath()+"/"+"stacks."+ finalFileName +".txt");
		writer = new BufferedWriter(new FileWriter(dumpFile));
		for (int i = finalList.size()-1; i>=0 ; i--){
			writer.write("Class Name>>>>>>"+finalList.get(i).getClassName()+"\t\t");
			writer.write("Method Name>>>>>>"+finalList.get(i).getMethodName()+"\t\t");
		}
		}catch (Exception e){
			e.printStackTrace();
			
		}finally{
			className.clear();
			finalList.clear();
			writer.close();
		}
	}
	

}
