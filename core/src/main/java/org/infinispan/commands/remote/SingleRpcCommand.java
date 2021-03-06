package org.infinispan.commands.remote;

import org.infinispan.commands.ReplicableCommand;
import org.infinispan.context.InvocationContext;

/**
 * Similar to {@link org.infinispan.commands.remote.MultipleRpcCommand}, but it only aggregates a single command for
 * replication.
 *
 * @author Mircea.Markus@jboss.com
 */
public class SingleRpcCommand extends BaseRpcInvokingCommand {
   public static final int COMMAND_ID = 1;

   private ReplicableCommand command;

   private SingleRpcCommand() {
      super(null); // For command id uniqueness test
   }

   public SingleRpcCommand(String cacheName, ReplicableCommand command) {
      super(cacheName);
      this.command = command;
   }

   public SingleRpcCommand(String cacheName) {
      super(cacheName);
   }

   @Override
   public void setParameters(int commandId, Object[] parameters) {
      if (commandId != COMMAND_ID) throw new IllegalStateException("Unusupported command id:" + commandId);
      command = (ReplicableCommand) parameters[0];
   }

   @Override
   public byte getCommandId() {
      return COMMAND_ID;
   }

   @Override
   public Object[] getParameters() {
      return new Object[]{command};
   }

   @Override
   public Object perform(InvocationContext ctx) throws Throwable {
      return processVisitableCommand(command);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof SingleRpcCommand)) return false;

      SingleRpcCommand that = (SingleRpcCommand) o;

      if (cacheName != null ? !cacheName.equals(that.cacheName) : that.cacheName != null) return false;
      if (command != null ? !command.equals(that.command) : that.command != null) return false;
      if (interceptorChain != null ? !interceptorChain.equals(that.interceptorChain) : that.interceptorChain != null)
         return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = interceptorChain != null ? interceptorChain.hashCode() : 0;
      result = 31 * result + (cacheName != null ? cacheName.hashCode() : 0);
      result = 31 * result + (command != null ? command.hashCode() : 0);
      return result;
   }

   @Override
   public String toString() {
      return "SingleRpcCommand{" +
            "cacheName='" + cacheName + '\'' +
            ", command=" + command +
            '}';
   }

   public ReplicableCommand getCommand() {
      return command;
   }

   @Override
   public boolean isReturnValueExpected() {
      return command.isReturnValueExpected();
   }

   @Override
   public boolean canBlock() {
      return command.canBlock();
   }
}
