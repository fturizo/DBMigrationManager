package fturizo.utils.dbmigration.entities;

import java.util.Date;

/**
 *
 * @author Fabio Andres
 */
public class Migration {
    
    private final Long versionRank;
    private final Long installedRank;
    private final String version;
    private final String description;
    private final String type;
    private final String script;
    private final String checksum;
    private final String installer;
    private final Date installDate;
    private final Long executionTime;
    private final boolean success;

    public Migration(Long versionRank, Long installedRank, String version, String description, 
                     String type, String script, String checksum, String installer, 
                     Date installDate, Long executionTime, boolean success) {
        this.versionRank = versionRank;
        this.installedRank = installedRank;
        this.version = version;
        this.description = description;
        this.type = type;
        this.script = script;
        this.checksum = checksum;
        this.installer = installer;
        this.installDate = installDate;
        this.executionTime = executionTime;
        this.success = success;
    }

    public Long getVersionRank() {
        return versionRank;
    }

    public Long getInstalledRank() {
        return installedRank;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getScript() {
        return script;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getInstaller() {
        return installer;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.version != null ? this.version.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Migration other = (Migration) obj;
        return !((this.version == null) ? (other.version != null) : !this.version.equals(other.version));
    }
}
