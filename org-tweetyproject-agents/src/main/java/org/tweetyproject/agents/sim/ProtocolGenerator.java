/*
 *  This file is part of "TweetyProject", a collection of Java libraries for
 *  logical aspects of artificial intelligence and knowledge representation.
 *
 *  TweetyProject is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License version 3 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright 2016 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package org.tweetyproject.agents.sim;

import org.tweetyproject.agents.Agent;
import org.tweetyproject.agents.MultiAgentSystem;
import org.tweetyproject.agents.Protocol;

/**
 * Generates protocols for simulation.
 * @author Matthias Thimm
 * @param <T> The actual type of the protocol.
 * @param <S> The actual type of the agents.
 * @param <R> The actual type of the multi-agent system.
 */
public interface ProtocolGenerator<T extends Protocol, S extends Agent, R extends MultiAgentSystem<S>> {

	/**
	 * Generates a new protocol.
	 * @param mas the multi-agent system.
	 * @param params this object can be used for sharing parameters across
	 *  the generating components of a simulation.
	 * @return a multi-agent system.
	 */
	public T generate(R mas, SimulationParameters params);	
}
