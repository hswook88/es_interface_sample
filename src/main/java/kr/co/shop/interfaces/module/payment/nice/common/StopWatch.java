package kr.co.shop.interfaces.module.payment.nice.common;

public class StopWatch {
	/**
	 * 시작 시간
	 */
	private long start = 0;

	/**
	 * 현재 시간
	 */
	private long current = 0;

	/**
	 * 생성자
	 */
	public StopWatch() {
		reset();
	}

	/**
	 * 초기화
	 */
	public void reset() {
		start = System.currentTimeMillis();
		current = start;
	}

	/**
	 * 이전측정한 시간에서부터 현시간까지의 경과시간을 리턴한다.
	 *
	 * @return 밀리세컨드
	 */
	public long getElapsed() {
		long now = System.currentTimeMillis();
		long elapsed = (now - current);
		current = now;

		return elapsed;
	}

	/**
	 * 최초 생성부터 현시간까지의 경과시간을 리턴한다.
	 *
	 * @return 밀리세컨드
	 */
	public long getTotalElapsed() {
		current = System.currentTimeMillis();

		return (current - start);
	}

	/**
	 * 이전측정한 시간에서부터 현시간까지의 경과시간을 리턴한다.
	 *
	 * @return 0h:0m:0s:000ms
	 */
	public String getTimeString() {
		return getTimeString(getElapsed());
	}

	/**
	 * 최초 생성부터 현시간까지의 경과시간을 리턴한다.
	 *
	 * @return 0h:0m:0s:000ms
	 */
	public String getTotalTimeString() {
		return getTimeString(getTotalElapsed());
	}

	/**
	 * 밀리세컨드를 0h:0m:0s:000ms 형태로 변경한다.
	 *
	 * @param time 밀리세컨드
	 *
	 * @return 0h:0m:0s:000ms
	 */
	protected String getTimeString(long time) {
		int i = 0x36ee80;
		int j = 60000;
		long l1 = time;
		int k = (int) (l1 / (long) i);
		l1 -= (k * i);

		int l = (int) (l1 / (long) j);
		l1 -= (l * j);

		int i1 = (int) (l1 / 1000L);
		l1 -= (i1 * 1000);

		int j1 = (int) l1;

		return k + "h:" + l + "m:" + i1 + "s:" + j1 + "ms";
	}

	/**
	 * getTotalTimeString()
	 *
	 * @return String
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("StopWatch value ( ");
		sb.append("totalElapsed=>" + getTotalElapsed() + " ) ");

		return sb.toString();
	}
}
